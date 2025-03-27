package br.com.itau.ensurancequote.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.MonthlyPremiumAmount;
import br.com.itau.ensurancequote.domains.models.Offer;
import br.com.itau.ensurancequote.domains.models.Product;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.domains.services.PremiumRangeValidationRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PremiumRangeValidationRuleTest {

    @Test
    void shouldThrowWhenPremiumBelowMin() {
        final PremiumRangeValidationRule rule = new PremiumRangeValidationRule();
        final Quote quote = createQuote(BigDecimal.valueOf(40));
        final Product product = createProduct(List.of("offer1"));
        final Offer offer = createOffer(
                new MonthlyPremiumAmount(
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(100),
                        BigDecimal.valueOf(60)
                )
        );
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldThrowWhenPremiumAboveMax() {
        final PremiumRangeValidationRule rule = new PremiumRangeValidationRule();
        final Quote quote = createQuote(BigDecimal.valueOf(120));
        final Product product = createProduct(List.of("offer1"));
        Offer offer = createOffer(
                new MonthlyPremiumAmount(
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(100),
                        BigDecimal.valueOf(60)
                )
        );
        assertThrows(QuoteValidationException.class, () -> rule.validate(quote, product, offer));
    }

    @Test
    void shouldNotThrowWhenPremiumInRange() {
        final PremiumRangeValidationRule rule = new PremiumRangeValidationRule();
        final Quote quote = createQuote(BigDecimal.valueOf(75));
        final Product product = createProduct(List.of("offer1"));
        final Offer offer = createOffer(
                new MonthlyPremiumAmount(
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(100),
                        BigDecimal.valueOf(60)
                )
        );
        assertDoesNotThrow(() -> rule.validate(quote, product, offer));
    }

    private Quote createQuote(final BigDecimal premium) {
        return new Quote(
                null,
                null,
                null,
                null,
                premium,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Product createProduct(final List<String> offers) {
        return new Product(
                "p1",
                "someProductName",
                LocalDateTime.now(),
                true,
                offers
        );
    }

    private Offer createOffer(final MonthlyPremiumAmount amountRange) {
        return new Offer(
                "offer1",
                "p1",
                "someOfferName",
                LocalDateTime.now(),
                true,
                Collections.emptyMap(),
                Collections.emptyList(),
                amountRange
        );
    }
}