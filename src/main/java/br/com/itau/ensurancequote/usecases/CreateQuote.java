package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.domains.exceptions.QuoteAlreadyExistsException;
import br.com.itau.ensurancequote.gateways.OfferGateway;
import br.com.itau.ensurancequote.gateways.ProductGateway;
import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.gateways.producers.MessageProducer;
import br.com.itau.ensurancequote.gateways.producers.dtos.QuoteReceivedEvent;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.QuoteValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class CreateQuote {
    private final QuoteGateway quoteGateway;
    private final QuoteValidationService quoteValidationService;
    private final ProductGateway productGateway;
    private final OfferGateway offerGateway;
    private final MessageProducer messageProducer;
    private final String quoteReceivedTopic;

    private static final Logger logger = LoggerFactory.getLogger(CreateQuote.class);

    public CreateQuote(final QuoteGateway quoteGateway,
                       final ProductGateway productGateway,
                       final OfferGateway offerGateway,
                       final MessageProducer messageProducer,
                       @Value("${quote.received.topic}")
                       final String quoteReceivedTopic) {
        this.quoteGateway = quoteGateway;
        this.quoteValidationService = new QuoteValidationService();
        this.productGateway = productGateway;
        this.offerGateway = offerGateway;
        this.messageProducer = messageProducer;
        this.quoteReceivedTopic = quoteReceivedTopic;
    }

    public Quote execute(final Quote quote) {
        logger.info("Starting CreateQuote for productId: {} and offerId {} ", quote.getProductId(), quote.getOfferId());

        final Product product = productGateway.get(quote.getProductId());
        final Offer offer = offerGateway.get(quote.getOfferId());
        quoteValidationService.validate(quote, product, offer);

        try {
            final Quote persistedQuote = quoteGateway.save(quote);

            messageProducer.send(mapTo(persistedQuote), quoteReceivedTopic);

            logger.info("Successfully created quote with id: {}", persistedQuote.getId());

            return persistedQuote;
        } catch (DataIntegrityViolationException ex) {
            throw new QuoteAlreadyExistsException("A quote with the given product, offer, and customer already exists.");
        }
    }

    public QuoteReceivedEvent mapTo(final Quote quote) {
        return new QuoteReceivedEvent(
                quote.getId(),
                quote.getProductId(),
                quote.getOfferId(),
                quote.getCategory(),
                quote.getTotalMonthlyPremiumAmount(),
                quote.getTotalCoverageAmount(),
                quote.getCoverages(),
                quote.getAssistances(),
                quote.getCustomer(),
                quote.getInsurancePolicyId(),
                quote.getCreatedAt(),
                quote.getUpdatedAt()
        );
    }
}
