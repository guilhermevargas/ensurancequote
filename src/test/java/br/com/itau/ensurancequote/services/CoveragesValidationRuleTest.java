package br.com.itau.ensurancequote.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.CoveragesValidationRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoveragesValidationRuleTest {

    @Test
    void shouldThrowWhenCoverageNotAllowed() {
        CoveragesValidationRule rule = new CoveragesValidationRule();
        Quote quote = createQuote(Map.of("InvalidCoverage", BigDecimal.valueOf(10)), BigDecimal.valueOf(10));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(Map.of("ValidCoverage", BigDecimal.valueOf(100)));
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldThrowWhenCoverageExceedsMax() {
        CoveragesValidationRule rule = new CoveragesValidationRule();
        Quote quote = createQuote(Map.of("Incendio", BigDecimal.valueOf(200)), BigDecimal.valueOf(200));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(Map.of("Incendio", BigDecimal.valueOf(100)));
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldThrowWhenSumDoesNotMatch() {
        CoveragesValidationRule rule = new CoveragesValidationRule();
        Quote quote = createQuote(Map.of("Incendio", BigDecimal.valueOf(50)), BigDecimal.valueOf(60));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(Map.of("Incendio", BigDecimal.valueOf(100)));
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldNotThrowWhenCoverageIsValid() {
        CoveragesValidationRule rule = new CoveragesValidationRule();
        Quote quote = createQuote(Map.of("Incendio", BigDecimal.valueOf(50)), BigDecimal.valueOf(50));
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(Map.of("Incendio", BigDecimal.valueOf(100)));
        assertDoesNotThrow(() -> rule.validate(quote, product, offer));
    }

    private Quote createQuote(Map<String, BigDecimal> coverages, BigDecimal totalCoverageAmount) {
        return new Quote(
                null,
                null,
                null,
                null,
                null,
                totalCoverageAmount,
                coverages,
                null,
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

    private Offer createOffer(Map<String, BigDecimal> coverages) {
        return new Offer(
                "offer1",
                "p1",
                "offerName",
                LocalDateTime.now(),
                true,
                coverages,
                null,
                null
        );
    }
}
