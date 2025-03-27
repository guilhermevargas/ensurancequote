package br.com.itau.ensurancequote.domains.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.TotalCoverageAmountValidationRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TotalCoverageAmountValidationRuleTest {

    @Test
    void shouldThrowWhenCoverageSumDoesNotMatchTotalCoverageAmount() {
        TotalCoverageAmountValidationRule rule = new TotalCoverageAmountValidationRule();
        Quote quote = createQuote(
                Map.of("Incendio", BigDecimal.valueOf(100), "RCivil", BigDecimal.valueOf(50)),
                BigDecimal.valueOf(200)
        );
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer();
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldNotThrowWhenCoverageSumMatchesTotalCoverageAmount() {
        TotalCoverageAmountValidationRule rule = new TotalCoverageAmountValidationRule();
        Quote quote = createQuote(
                Map.of("Incendio", BigDecimal.valueOf(100), "RCivil", BigDecimal.valueOf(100)),
                BigDecimal.valueOf(200)
        );
        Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer();
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

    private Offer createOffer() {
        return new Offer(
                "offer1",
                "p1",
                "someOfferName",
                LocalDateTime.now(),
                true,
                Map.of(),
                List.of(),
                null
        );
    }

}
