package br.com.itau.ensurancequote.services;

import br.com.itau.ensurancequote.domains.exceptions.QuoteValidationException;
import br.com.itau.ensurancequote.domains.models.*;
import br.com.itau.ensurancequote.domains.services.QuoteValidationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuoteValidationServiceTest {

    @Test
    void shouldThrow_whenAnyRuleFails() {
        QuoteValidationService service = new QuoteValidationService();

        Quote invalidQuote = new Quote(
                null,
                null,
                null,
                null,
                BigDecimal.valueOf(60),
                BigDecimal.valueOf(120000),
                Map.of("SomeCoverage", BigDecimal.valueOf(50000)),
                List.of("Assist1"),
                new Customer("123", "John Doe", "NATURAL", "MALE", LocalDate.now(), "test@test.com", "5555555"),
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Product product = null;
        Offer offer = null;

        assertThrows(
                QuoteValidationException.class,
                () -> service.validate(invalidQuote, product, offer)
        );
    }

    @Test
    void shouldNotThrow_whenAllRulesPass() {
        QuoteValidationService service = new QuoteValidationService();

        Quote validQuote = new Quote(
                null,
                "p1",
                "offer1",
                "HOME",
                BigDecimal.valueOf(75),
                BigDecimal.valueOf(550000),
                Map.of(
                        "Incendio", BigDecimal.valueOf(250000),
                        "Desastres naturais", BigDecimal.valueOf(300000)
                ),
                List.of("Assist1", "Assist2"),
                new Customer("123", "John Doe", "NATURAL", "MALE", LocalDate.of(1980, 5, 1), "john@test.com", "5555"),
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Product product = new Product(
                "p1",
                "Seguro Residencial",
                LocalDateTime.now(),
                true,
                List.of("offer1")
        );

        Offer offer = new Offer(
                "offer1",
                "p1",
                "Seguro Casa",
                LocalDateTime.now(),
                true,
                Map.of(
                        "Incendio", BigDecimal.valueOf(300000),
                        "Desastres naturais", BigDecimal.valueOf(400000)
                ),
                List.of("Assist1", "Assist2"),
                new MonthlyPremiumAmount(BigDecimal.valueOf(50), BigDecimal.valueOf(100), BigDecimal.valueOf(60))
        );

        assertDoesNotThrow(
                () -> service.validate(validQuote, product, offer)
        );
    }
}