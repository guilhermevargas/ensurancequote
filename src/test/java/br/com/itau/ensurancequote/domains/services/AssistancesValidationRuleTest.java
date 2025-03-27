package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.AssistancesValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AssistancesValidationRuleTest {

    @Test
    void shouldThrowWhenAssistanceNotInOffer() {
        AssistancesValidationRule rule = new AssistancesValidationRule();
        Quote quote = createQuote(List.of("InvalidAssist"));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(List.of("ValidAssist"));
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }
    @Test
    void shouldNotThrowWhenAllAssistancesAreValid() {
        AssistancesValidationRule rule = new AssistancesValidationRule();
        Quote quote = createQuote(List.of("Assist1", "Assist2"));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(List.of("Assist1", "Assist2", "Assist3"));
        assertDoesNotThrow(() -> rule.validate(quote, product, offer));
    }

    private Quote createQuote(List<String> assistances) {
        return new Quote(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                assistances,
                null,
                null,
                null,
                null
        );
    }
    private Product createProduct(List<String> offers) {
        return new Product(
                "p1",
                "someProductName",
                LocalDateTime.now(),
                true,
                offers
        );
    }
    private Offer createOffer(List<String> assistances) {
        return new Offer(
                "offer1",
                "p1",
                "someOfferName",
                LocalDateTime.now(),
                true,
                Map.of(),
                assistances,
                null
        );
    }
}
