package br.com.itau.ensurancequote.gateways.producers.dtos;

import br.com.itau.ensurancequote.domains.models.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record QuoteReceivedEvent(
        Long quoteId,
        String productId,
        String offerId,
        String category,
        BigDecimal totalMonthlyPremiumAmount,
        BigDecimal totalCoverageAmount,
        Map<String, BigDecimal> coverages,
        List<String> assistances,
        Customer customer,
        Long insurancePolicyId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}