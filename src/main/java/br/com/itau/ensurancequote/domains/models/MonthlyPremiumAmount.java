package br.com.itau.ensurancequote.domains.models;

import java.math.BigDecimal;
import java.util.Objects;

public final class MonthlyPremiumAmount {

    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal suggestedAmount;

    public MonthlyPremiumAmount(
            BigDecimal minAmount,
            BigDecimal maxAmount,
            BigDecimal suggestedAmount
    ) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.suggestedAmount = suggestedAmount;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public BigDecimal getSuggestedAmount() {
        return suggestedAmount;
    }

    @Override
    public String toString() {
        return "MonthlyPremiumAmount{" +
                "minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", suggestedAmount=" + suggestedAmount +
                '}';
    }
}

