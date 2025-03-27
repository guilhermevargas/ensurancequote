package br.com.itau.ensurancequote.domains.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonthlyPremiumAmountTest {

    @Test
    void testGettersAndToString() {
        MonthlyPremiumAmount premium = new MonthlyPremiumAmount(
                BigDecimal.valueOf(50.0),
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(75.0)
        );

        assertEquals(BigDecimal.valueOf(50.0), premium.getMinAmount());
        assertEquals(BigDecimal.valueOf(100.0), premium.getMaxAmount());
        assertEquals(BigDecimal.valueOf(75.0), premium.getSuggestedAmount());

        String toStringOutput = premium.toString();
        assertTrue(toStringOutput.contains("minAmount=" + premium.getMinAmount().toString()));
        assertTrue(toStringOutput.contains("maxAmount=" + premium.getMaxAmount().toString()));
        assertTrue(toStringOutput.contains("suggestedAmount=" + premium.getSuggestedAmount().toString()));
    }
}