package br.com.itau.ensurancequote.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record CreateQuoteRequest(
        @NotBlank(message = "Product ID must not be blank") String productId,
        @NotBlank(message = "Offer ID must not be blank") String offerId,
        @NotBlank(message = "Category must not be blank") String category,
        @NotNull(message = "Total monthly premium amount must not be null") BigDecimal totalMonthlyPremiumAmount,
        @NotNull(message = "Total coverage amount must not be null") BigDecimal totalCoverageAmount,
        @NotNull(message = "Coverages must not be null")
        @NotEmpty(message = "Coverages must not be empty") Map<String, BigDecimal> coverages,
        @NotNull(message = "Assistances must not be null")
        @NotEmpty(message = "Assistances must not be empty") List<String> assistances,
        @NotNull(message = "Customer must not be null") CustomerQuote customer
) {}