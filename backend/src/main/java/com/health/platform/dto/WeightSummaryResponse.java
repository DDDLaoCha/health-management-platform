package com.health.platform.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeightSummaryResponse {

    private BigDecimal initialWeight;
    private BigDecimal latestWeight;
    private BigDecimal change;

    public static WeightSummaryResponse empty() {
        return new WeightSummaryResponse(null, null, null);
    }

    public static WeightSummaryResponse of(BigDecimal initialWeight, BigDecimal latestWeight) {
        BigDecimal change = latestWeight.subtract(initialWeight).setScale(2, RoundingMode.HALF_UP);
        return new WeightSummaryResponse(initialWeight, latestWeight, change);
    }

    private WeightSummaryResponse(BigDecimal initialWeight, BigDecimal latestWeight, BigDecimal change) {
        this.initialWeight = initialWeight;
        this.latestWeight = latestWeight;
        this.change = change;
    }

    public BigDecimal getInitialWeight() { return initialWeight; }
    public BigDecimal getLatestWeight() { return latestWeight; }
    public BigDecimal getChange() { return change; }
}
