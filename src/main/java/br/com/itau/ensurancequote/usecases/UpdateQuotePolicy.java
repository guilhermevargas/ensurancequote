package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class UpdateQuotePolicy {
    private static final Logger logger = LoggerFactory.getLogger(UpdateQuotePolicy.class);
    private final QuoteGateway quoteGateway;

    public UpdateQuotePolicy(final QuoteGateway quoteGateway) {
        this.quoteGateway = quoteGateway;
    }

    public void execute(Long quoteId, Long policyNumber) {
        logger.info("Updating quote {} with policy number {}", quoteId, policyNumber);
        final Quote quote = quoteGateway.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found: " + quoteId));
        final Quote updatedQuote = mapTo(policyNumber, quote);
        final Quote saved = quoteGateway.save(updatedQuote);
        logger.info("Quote {} updated successfully", saved.getId());
    }

    private Quote mapTo(Long policyNumber, Quote quote) {
        return new Quote(
                quote.getId(),
                quote.getProductId(),
                quote.getOfferId(),
                quote.getCategory(),
                quote.getTotalMonthlyPremiumAmount(),
                quote.getTotalCoverageAmount(),
                quote.getCoverages(),
                quote.getAssistances(),
                quote.getCustomer(),
                policyNumber,
                quote.getCreatedAt(),
                now()
        );
    }
}
