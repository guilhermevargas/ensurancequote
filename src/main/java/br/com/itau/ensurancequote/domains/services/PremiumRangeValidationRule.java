package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.MonthlyPremiumAmount;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.math.BigDecimal;

public class PremiumRangeValidationRule implements QuoteValidationRule {

    @Override
    public void validate(Quote quote, Product product, Offer offer) throws QuoteValidationException {
        BigDecimal premium = quote.getTotalMonthlyPremiumAmount();
        MonthlyPremiumAmount range = offer.getMonthlyPremiumAmount();
        if (premium.compareTo(range.getMinAmount()) < 0 || premium.compareTo(range.getMaxAmount()) > 0) {
            throw new QuoteValidationException("Premium out of allowed range");
        }
    }
}
