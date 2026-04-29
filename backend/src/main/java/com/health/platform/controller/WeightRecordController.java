package com.health.platform.controller;

import com.health.platform.dto.CreateWeightRecordRequest;
import com.health.platform.dto.UpdateWeightRecordRequest;
import com.health.platform.dto.WeightRecordResponse;
import com.health.platform.service.WeightRecordService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weight-records")
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    public WeightRecordController(WeightRecordService weightRecordService) {
        this.weightRecordService = weightRecordService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WeightRecordResponse create(@Valid @RequestBody CreateWeightRecordRequest req) {
        return weightRecordService.create(req);
    }

    @GetMapping
    public List<WeightRecordResponse> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return weightRecordService.getAll(startDate, endDate);
    }

    @PutMapping("/{id}")
    public WeightRecordResponse update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateWeightRecordRequest req) {
        return weightRecordService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        weightRecordService.delete(id);
    }
}
