package br.com.itau.ensurancequote.controllers.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record QuoteResponse(
        Long id,
        String productId,
        String offerId,
        String category,
        BigDecimal totalMonthlyPremiumAmount,
        BigDecimal totalCoverageAmount,
        Map<String, BigDecimal> coverages,
        List<String> assistances,
        CustomerQuote customer,
        Long insurancePolicyId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
