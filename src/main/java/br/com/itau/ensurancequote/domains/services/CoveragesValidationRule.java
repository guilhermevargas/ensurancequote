package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.math.BigDecimal;
import java.util.Map.Entry;

import static java.math.BigDecimal.ZERO;

public class CoveragesValidationRule implements QuoteValidationRule {

    @Override
    public void validate(final Quote quote, final Product product, final Offer offer) throws QuoteValidationException {
        final BigDecimal sum = quote.getCoverages().entrySet().stream()
                .peek(entry -> {
                    var matchingOffer = getMatchingOffer(offer, entry);
                    if (entry.getValue().compareTo(matchingOffer.getValue()) > 0) {
                        throw new QuoteValidationException("Coverage exceeds limit: " + entry.getKey());
                    }
                })
                .map(Entry::getValue)
                .reduce(ZERO, BigDecimal::add);

        if (sum.compareTo(quote.getTotalCoverageAmount()) != 0) {
            throw new QuoteValidationException("Sum of coverages mismatch");
        }
    }

    private Entry<String, BigDecimal> getMatchingOffer(Offer offer, Entry<String, BigDecimal> entry) {
        return offer.getCoverages().entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(entry.getKey()))
                .findFirst()
                .orElseThrow(() -> new QuoteValidationException("Coverage not allowed: " + entry.getKey()));
    }

}
