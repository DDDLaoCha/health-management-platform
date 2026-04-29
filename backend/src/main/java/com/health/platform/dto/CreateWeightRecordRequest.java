package com.health.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateWeightRecordRequest {

    @NotNull(message = "recordDate is required")
    private LocalDate recordDate;

    @NotNull(message = "weightKg is required")
    @DecimalMin(value = "0.1", message = "weightKg must be greater than 0")
    private BigDecimal weightKg;

    private String note;

    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }

    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
