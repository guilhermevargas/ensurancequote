package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.util.List;

public class AssistancesValidationRule implements QuoteValidationRule {

    @Override
    public void validate(Quote quote, Product product, Offer offer) throws QuoteValidationException {
        List<String> requested = quote.getAssistances();
        List<String> allowed = offer.getAssistances();
        for (String a : requested) {
            if (!allowed.contains(a)) {
                throw new QuoteValidationException("Assistance not allowed: " + a);
            }
        }
    }
}
