package com.health.platform.dto;

import com.health.platform.entity.WeightRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeightRecordResponse {

    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private BigDecimal weightKg;
    private String note;
    private LocalDateTime createdAt;

    public static WeightRecordResponse from(WeightRecord record) {
        WeightRecordResponse resp = new WeightRecordResponse();
        resp.id = record.getId();
        resp.userId = record.getUser().getId();
        resp.recordDate = record.getRecordDate();
        resp.weightKg = record.getWeightKg();
        resp.note = record.getNote();
        resp.createdAt = record.getCreatedAt();
        return resp;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public LocalDate getRecordDate() { return recordDate; }
    public BigDecimal getWeightKg() { return weightKg; }
    public String getNote() { return note; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
