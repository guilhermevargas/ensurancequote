package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;

public class ActiveProductOfferValidationRule implements QuoteValidationRule {
    @Override
    public void validate(Quote quote, Product product, Offer offer) throws QuoteValidationException {
        if (product == null || !product.isActive()) {
            throw new QuoteValidationException("Product inactive or missing");
        }
        if (offer == null || !offer.isActive()) {
            throw new QuoteValidationException("Offer inactive or missing");
        }
        if (!product.getOffers().contains(offer.getId())) {
            throw new QuoteValidationException("Offer does not belong to product");
        }
    }
}
