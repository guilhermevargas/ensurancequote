package br.com.itau.ensurancequote.gateways.repositories.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quotes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "offer_id", "document_number"}))
public class QuoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;
    @Column(name = "offer_id", nullable = false)
    private String offerId;
    private String category;
    private BigDecimal totalMonthlyPremiumAmount;
    private BigDecimal totalCoverageAmount;

    @ElementCollection
    @CollectionTable(
            name = "quote_coverages",
            joinColumns = @JoinColumn(name = "quote_id")
    )
    @MapKeyColumn(name = "coverage_name")
    @Column(name = "coverage_amount")
    private Map<String, BigDecimal> coverages = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "quote_assistances",
            joinColumns = @JoinColumn(name = "quote_id")
    )
    @Column(name = "assistance_name")
    private List<String> assistances = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "documentNumber", column = @Column(name = "document_number", nullable = false))
    })
    private CustomerEmbedded customer;

    private Long insurancePolicyId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOfferId() {
        return offerId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalMonthlyPremiumAmount() {
        return totalMonthlyPremiumAmount;
    }
    public void setTotalMonthlyPremiumAmount(BigDecimal totalMonthlyPremiumAmount) {
        this.totalMonthlyPremiumAmount = totalMonthlyPremiumAmount;
    }

    public BigDecimal getTotalCoverageAmount() {
        return totalCoverageAmount;
    }
    public void setTotalCoverageAmount(BigDecimal totalCoverageAmount) {
        this.totalCoverageAmount = totalCoverageAmount;
    }

    public Map<String, BigDecimal> getCoverages() {
        return coverages;
    }
    public void setCoverages(Map<String, BigDecimal> coverages) {
        this.coverages = coverages;
    }

    public List<String> getAssistances() {
        return assistances;
    }
    public void setAssistances(List<String> assistances) {
        this.assistances = assistances;
    }

    public CustomerEmbedded getCustomer() {
        return customer;
    }
    public void setCustomer(CustomerEmbedded customer) {
        this.customer = customer;
    }

    public Long getInsurancePolicyId() {
        return insurancePolicyId;
    }
    public void setInsurancePolicyId(Long insurancePolicyId) {
        this.insurancePolicyId = insurancePolicyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
