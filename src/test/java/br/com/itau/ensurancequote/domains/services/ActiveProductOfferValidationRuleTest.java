package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.ActiveProductOfferValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActiveProductOfferValidationRuleTest {

    @Test
    void shouldThrowWhenProductIsNull() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Offer offer = createOffer(true);
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, null, offer));
    }

    @Test
    void shouldThrowWhenProductInactive() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Product product = createProduct(false, List.of("offer1"));
        Offer offer = createOffer(true);
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldThrowWhenOfferIsNull() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Product product = createProduct(true, List.of("offer1"));
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, null));
    }

    @Test
    void shouldThrowWhenOfferInactive() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Product product = createProduct(true, List.of("offer1"));
        Offer offer = createOffer(false);
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldThrowWhenOfferNotBelongsToProduct() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Product product = createProduct(true, List.of("offer2"));
        Offer offer = createOffer(true);
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldNotThrowWhenProductAndOfferAreValid() {
        ActiveProductOfferValidationRule rule = new ActiveProductOfferValidationRule();
        Quote quote = createMinimalQuote();
        Product product = createProduct(true, List.of("offer1"));
        Offer offer = createOffer(true);
        assertDoesNotThrow(() -> rule.validate(quote, product, offer));
    }

    private Quote createMinimalQuote() {
        return new Quote(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Product createProduct(boolean active, List<String> offers) {
        return new Product(
                "p1",
                "someProductName",
                LocalDateTime.now(),
                active,
                offers
        );
    }

    private Offer createOffer(boolean active) {
        return new Offer(
                "offer1",
                "p1",
                "someOfferName",
                LocalDateTime.now(),
                active,
                null,
                null,
                null
        );
    }

}
