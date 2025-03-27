package br.com.itau.ensurancequote.domains.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OfferTest {

    @Test
    void testGetters() {
        LocalDateTime now = LocalDateTime.now();
        MonthlyPremiumAmount premium = new MonthlyPremiumAmount(
                BigDecimal.valueOf(50.0),
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(75.0)
        );
        Map<String, BigDecimal> coverages = Map.of("Incendio", BigDecimal.valueOf(500000.0));
        List<String> assistances = List.of("Encanador", "Eletricista");
        Offer offer = new Offer("offer1", "prod1", "Seguro de Vida Familiar", now, true, coverages, assistances, premium);

        assertEquals("offer1", offer.getId());
        assertEquals("prod1", offer.getProductId());
        assertEquals("Seguro de Vida Familiar", offer.getName());
        assertEquals(now, offer.getCreatedAt());
        assertTrue(offer.isActive());
        assertEquals(coverages, offer.getCoverages());
        assertEquals(assistances, offer.getAssistances());
        assertEquals(premium, offer.getMonthlyPremiumAmount());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        MonthlyPremiumAmount premium1 = new MonthlyPremiumAmount(
                BigDecimal.valueOf(50.0),
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(75.0)
        );
        Offer offer1 = new Offer("offer1", "prod1", "Seguro de Vida Familiar", now, true,
                Map.of("Incendio", BigDecimal.valueOf(500000.0)),
                List.of("Encanador"),
                premium1);
        Offer offer2 = new Offer("offer1", "prod1", "Different Name", now.plusMinutes(5), false,
                Map.of("Incendio", BigDecimal.valueOf(600000.0)),
                List.of("Eletricista"),
                premium1);
        Offer offer3 = new Offer("offer3", "prod2", "Seguro de Vida Familiar", now, true,
                Map.of(), List.of(), premium1);

        assertEquals(offer1, offer2);
        assertEquals(offer1.hashCode(), offer2.hashCode());
        assertNotEquals(offer1, offer3);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        MonthlyPremiumAmount premium = new MonthlyPremiumAmount(
                BigDecimal.valueOf(50.0),
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(75.0)
        );
        Offer offer = new Offer("offer1", "prod1", "Seguro de Vida Familiar", now, true,
                Map.of("Incendio", BigDecimal.valueOf(500000.0)),
                List.of("Encanador"),
                premium);
        String str = offer.toString();
        assertTrue(str.contains("offer1"));
        assertTrue(str.contains("prod1"));
        assertTrue(str.contains("Seguro de Vida Familiar"));
        assertTrue(str.contains("Incendio"));
        assertTrue(str.contains("Encanador"));
        assertTrue(str.contains(premium.toString()));
    }
}
