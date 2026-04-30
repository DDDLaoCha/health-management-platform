package com.health.platform.service;

import com.health.platform.dto.CreateWeightRecordRequest;
import com.health.platform.dto.UpdateWeightRecordRequest;
import com.health.platform.dto.WeightRecordResponse;
import com.health.platform.dto.WeightSummaryResponse;
import com.health.platform.entity.User;
import com.health.platform.entity.WeightRecord;
import com.health.platform.repository.UserRepository;
import com.health.platform.repository.WeightRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeightRecordService {

    private final UserRepository userRepository;
    private final WeightRecordRepository weightRecordRepository;

    public WeightRecordService(UserRepository userRepository,
                               WeightRecordRepository weightRecordRepository) {
        this.userRepository = userRepository;
        this.weightRecordRepository = weightRecordRepository;
    }

    @Transactional
    public WeightRecordResponse create(Long userId, CreateWeightRecordRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id=" + userId + " not found."));

        boolean exists = weightRecordRepository
                .findByUserIdAndRecordDate(userId, req.getRecordDate())
                .isPresent();
        if (exists) {
            throw new IllegalArgumentException(
                    "A weight record for " + req.getRecordDate() + " already exists.");
        }

        WeightRecord record = new WeightRecord();
        record.setUser(user);
        record.setRecordDate(req.getRecordDate());
        record.setWeightKg(req.getWeightKg());
        record.setNote(req.getNote());

        return WeightRecordResponse.from(weightRecordRepository.save(record));
    }

    @Transactional(readOnly = true)
    public List<WeightRecordResponse> getAll(Long userId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "startDate must not be after endDate.");
        }

        List<WeightRecord> records;
        if (startDate != null && endDate != null) {
            records = weightRecordRepository
                    .findByUserIdAndRecordDateBetweenOrderByRecordDateAsc(userId, startDate, endDate);
        } else if (startDate != null) {
            records = weightRecordRepository
                    .findByUserIdAndRecordDateGreaterThanEqualOrderByRecordDateAsc(userId, startDate);
        } else if (endDate != null) {
            records = weightRecordRepository
                    .findByUserIdAndRecordDateLessThanEqualOrderByRecordDateAsc(userId, endDate);
        } else {
            records = weightRecordRepository.findByUserIdOrderByRecordDateAsc(userId);
        }

        return records.stream().map(WeightRecordResponse::from).toList();
    }

    @Transactional
    public WeightRecordResponse update(Long userId, Long id, UpdateWeightRecordRequest req) {
        WeightRecord record = weightRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Weight record with id=" + id + " not found."));

        if (!record.getUser().getId().equals(userId)) {
            throw new IllegalStateException(
                    "Weight record with id=" + id + " not found.");
        }

        if (!record.getRecordDate().equals(req.getRecordDate())) {
            weightRecordRepository
                    .findByUserIdAndRecordDate(userId, req.getRecordDate())
                    .ifPresent(conflict -> {
                        throw new IllegalArgumentException(
                                "A weight record for " + req.getRecordDate() + " already exists.");
                    });
        }

        record.setRecordDate(req.getRecordDate());
        record.setWeightKg(req.getWeightKg());
        record.setNote(req.getNote());

        return WeightRecordResponse.from(weightRecordRepository.save(record));
    }

    @Transactional
    public void delete(Long userId, Long id) {
        WeightRecord record = weightRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Weight record with id=" + id + " not found."));

        if (!record.getUser().getId().equals(userId)) {
            throw new IllegalStateException(
                    "Weight record with id=" + id + " not found.");
        }

        weightRecordRepository.delete(record);
    }

    @Transactional(readOnly = true)
    public WeightSummaryResponse getSummary(Long userId) {
        var initial = weightRecordRepository.findFirstByUserIdOrderByRecordDateAsc(userId);
        if (initial.isEmpty()) {
            return WeightSummaryResponse.empty();
        }
        var latest = weightRecordRepository.findFirstByUserIdOrderByRecordDateDesc(userId).orElseThrow();
        return WeightSummaryResponse.of(initial.get().getWeightKg(), latest.getWeightKg());
    }
}
