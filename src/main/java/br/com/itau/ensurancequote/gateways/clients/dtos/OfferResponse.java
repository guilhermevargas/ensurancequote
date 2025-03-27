package br.com.itau.ensurancequote.gateways.clients.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record OfferResponse(
        String id,
        String productId,
        String name,
        LocalDateTime createdAt,
        boolean active,
        Map<String, BigDecimal> coverages,
        List<String> assistances,
        MonthlyPremiumAmount monthlyPremiumAmount
) {
    public record MonthlyPremiumAmount(
            BigDecimal maxAmount,
            BigDecimal minAmount,
            BigDecimal suggestedAmount
    ) {}
}
