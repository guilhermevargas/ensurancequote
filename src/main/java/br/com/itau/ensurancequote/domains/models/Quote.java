package br.com.itau.ensurancequote.domains.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.Objects;

public final class Quote {

    private final Long id;
    private final String productId;
    private final String offerId;
    private final String category;
    private final BigDecimal totalMonthlyPremiumAmount;
    private final BigDecimal totalCoverageAmount;
    private final Map<String, BigDecimal> coverages;
    private final List<String> assistances;
    private final Customer customer;
    private final Long insurancePolicyId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Quote(
            Long id,
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
            LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.offerId = offerId;
        this.category = category;
        this.totalMonthlyPremiumAmount = totalMonthlyPremiumAmount;
        this.totalCoverageAmount = totalCoverageAmount;
        this.coverages = coverages == null
                ? Collections.emptyMap()
                : Map.copyOf(coverages);
        this.assistances = assistances == null
                ? Collections.emptyList()
                : List.copyOf(assistances);
        this.customer = customer;
        this.insurancePolicyId = insurancePolicyId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Quote assignPolicy(Long newPolicyId) {
        return new Quote(
                this.id,
                this.productId,
                this.offerId,
                this.category,
                this.totalMonthlyPremiumAmount,
                this.totalCoverageAmount,
                this.coverages,
                this.assistances,
                this.customer,
                newPolicyId,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getTotalMonthlyPremiumAmount() {
        return totalMonthlyPremiumAmount;
    }

    public BigDecimal getTotalCoverageAmount() {
        return totalCoverageAmount;
    }

    public Map<String, BigDecimal> getCoverages() {
        return coverages;
    }

    public List<String> getAssistances() {
        return assistances;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getInsurancePolicyId() {
        return insurancePolicyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote quote)) return false;
        return Objects.equals(productId, quote.productId)
                && Objects.equals(offerId, quote.offerId)
                && Objects.equals(category, quote.category)
                && Objects.equals(customer.getDocumentNumber(), quote.customer.getDocumentNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, offerId, customer.getDocumentNumber());
    }
}
