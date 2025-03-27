package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.util.List;

public class QuoteValidationService {
    private final List<QuoteValidationRule> rules;

    public QuoteValidationService() {
        this.rules = List.of(
                new ActiveProductOfferValidationRule(),
                new AssistancesValidationRule(),
                new CoveragesValidationRule(),
                new PremiumRangeValidationRule(),
                new TotalCoverageAmountValidationRule()
        );
    }

    public void validate(Quote quote, Product product, Offer offer) throws QuoteValidationException {
        rules.forEach(validators -> validators.validate(quote, product, offer));
    }
}
