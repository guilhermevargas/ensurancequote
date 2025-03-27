package br.com.itau.ensurancequote.domains.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class QuoteTest {

    @Test
    void testGetters() {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(
                "123456789", "John Doe", "NATURAL", "MALE",
                LocalDate.of(1980, 1, 1), "john@example.com", "1234567890"
        );
        Map<String, BigDecimal> coverages = Map.of("Coverage1", BigDecimal.valueOf(1000));
        List<String> assistances = List.of("Assistance1");
        Quote quote = new Quote(1L, "prod1", "offer1", "HOME",
                BigDecimal.valueOf(75.25), BigDecimal.valueOf(825000),
                coverages, assistances, customer, null, now, now);

        assertEquals(1L, quote.getId());
        assertEquals("prod1", quote.getProductId());
        assertEquals("offer1", quote.getOfferId());
        assertEquals("HOME", quote.getCategory());
        assertEquals(BigDecimal.valueOf(75.25), quote.getTotalMonthlyPremiumAmount());
        assertEquals(BigDecimal.valueOf(825000), quote.getTotalCoverageAmount());
        assertEquals(coverages, quote.getCoverages());
        assertEquals(assistances, quote.getAssistances());
        assertEquals(customer, quote.getCustomer());
        assertNull(quote.getInsurancePolicyId());
    }

    @Test
    void testAssignPolicy() {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(
                "123456789", "John Doe", "NATURAL", "MALE",
                LocalDate.of(1980, 1, 1), "john@example.com", "1234567890"
        );
        Quote quote = new Quote(1L, "prod1", "offer1", "HOME",
                BigDecimal.valueOf(75.25), BigDecimal.valueOf(825000),
                Map.of("Coverage1", BigDecimal.valueOf(1000)),
                List.of("Assistance1"), customer, null, now, now);
        Quote updatedQuote = quote.assignPolicy(555L);
        assertNotNull(updatedQuote);
        assertEquals(555L, updatedQuote.getInsurancePolicyId());
        assertEquals(quote.getId(), updatedQuote.getId());
        assertTrue(updatedQuote.getUpdatedAt().isAfter(quote.getUpdatedAt()));
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Customer customer1 = new Customer(
                "123456789", "John Doe", "NATURAL", "MALE",
                LocalDate.of(1980, 1, 1), "john@example.com", "1234567890"
        );
        Customer customer2 = new Customer(
                "123456789", "Jane Doe", "NATURAL", "FEMALE",
                LocalDate.of(1990, 1, 1), "jane@example.com", "0987654321"
        );
        Quote quote1 = new Quote(1L, "prod1", "offer1", "HOME",
                BigDecimal.valueOf(75.25), BigDecimal.valueOf(825000),
                Map.of("Coverage1", BigDecimal.valueOf(1000)),
                List.of("Assistance1"), customer1, null, now, now);
        Quote quote2 = new Quote(2L, "prod1", "offer1", "HOME",
                BigDecimal.valueOf(80.0), BigDecimal.valueOf(830000),
                Map.of("Coverage1", BigDecimal.valueOf(1200)),
                List.of("Assistance2"), customer2, null, now, now.plusSeconds(10));
        Quote quote3 = new Quote(3L, "prod2", "offer2", "AUTO",
                BigDecimal.valueOf(100.0), BigDecimal.valueOf(500000),
                Map.of(), List.of(), customer1, null, now, now);

        assertEquals(quote1, quote2);
        assertEquals(quote1.hashCode(), quote2.hashCode());
        assertNotEquals(quote1, quote3);
    }
}
