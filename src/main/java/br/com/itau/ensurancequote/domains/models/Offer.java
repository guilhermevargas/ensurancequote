package br.com.itau.ensurancequote.domains.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Offer {

    private final String id;
    private final String productId;
    private final String name;
    private final LocalDateTime createdAt;
    private final boolean active;
    private final Map<String, BigDecimal> coverages;
    private final List<String> assistances;
    private final MonthlyPremiumAmount monthlyPremiumAmount;

    public Offer(
            String id,
            String productId,
            String name,
            LocalDateTime createdAt,
            boolean active,
            Map<String, BigDecimal> coverages,
            List<String> assistances,
            MonthlyPremiumAmount monthlyPremiumAmount
    ) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.createdAt = createdAt;
        this.active = active;
        this.coverages = (coverages == null)
                ? Collections.emptyMap()
                : Map.copyOf(coverages);
        this.assistances = (assistances == null)
                ? Collections.emptyList()
                : List.copyOf(assistances);
        this.monthlyPremiumAmount = monthlyPremiumAmount;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public Map<String, BigDecimal> getCoverages() {
        return coverages;
    }

    public List<String> getAssistances() {
        return assistances;
    }

    public MonthlyPremiumAmount getMonthlyPremiumAmount() {
        return monthlyPremiumAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer offer)) return false;
        return Objects.equals(id, offer.id)
                && Objects.equals(productId, offer.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", active=" + active +
                ", coverages=" + coverages +
                ", assistances=" + assistances +
                ", monthlyPremiumAmount=" + monthlyPremiumAmount +
                '}';
    }
}

