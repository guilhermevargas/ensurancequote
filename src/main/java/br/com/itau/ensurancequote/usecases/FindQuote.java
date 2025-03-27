package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.domains.exceptions.ProductNotFoundException;
import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FindQuote {
    private static final Logger logger = LoggerFactory.getLogger(FindQuote.class);
    private final QuoteGateway quoteGateway;

    public FindQuote(final QuoteGateway quoteGateway) {
        this.quoteGateway = quoteGateway;
    }

    public Quote execute(final Long quoteId) {
        logger.info("Finding quote with id: {}", quoteId);
        final Quote quote = quoteGateway.findById(quoteId)
                .orElseThrow(() -> {
                    logger.warn("Quote with id {} not found", quoteId);
                    return new ProductNotFoundException("Quote not found with id: " + quoteId);
                });
        logger.info("Found quote: {}", quote.getId());
        return quote;
    }
}
