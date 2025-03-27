package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

public class TotalCoverageAmountValidationRule implements QuoteValidationRule {
    @Override
    public void validate(Quote quote, Product product, Offer offer) throws QuoteValidationException {
        final Map<String, BigDecimal> requestedCoverages = quote.getCoverages();
        final BigDecimal expectedTotal = quote.getTotalCoverageAmount();
        final BigDecimal sum = requestedCoverages.values()
                .stream()
                .reduce(ZERO, BigDecimal::add);

        if (sum.compareTo(expectedTotal) != 0) {
            throw new QuoteValidationException("Sum of coverages does not match totalCoverageAmount");
        }
    }
}
