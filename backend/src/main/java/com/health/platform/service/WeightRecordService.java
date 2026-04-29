package com.health.platform.service;

import com.health.platform.dto.CreateWeightRecordRequest;
import com.health.platform.dto.UpdateWeightRecordRequest;
import com.health.platform.dto.WeightRecordResponse;
import com.health.platform.entity.User;
import com.health.platform.entity.WeightRecord;
import com.health.platform.repository.UserRepository;
import com.health.platform.repository.WeightRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeightRecordService {

    private static final long DEFAULT_USER_ID = 1L;

    private final UserRepository userRepository;
    private final WeightRecordRepository weightRecordRepository;

    public WeightRecordService(UserRepository userRepository,
                               WeightRecordRepository weightRecordRepository) {
        this.userRepository = userRepository;
        this.weightRecordRepository = weightRecordRepository;
    }

    @Transactional
    public WeightRecordResponse create(CreateWeightRecordRequest req) {
        User user = userRepository.findById(DEFAULT_USER_ID)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id=" + DEFAULT_USER_ID + " not found. Please insert a seed user first."));

        boolean exists = weightRecordRepository
                .findByUserIdAndRecordDate(DEFAULT_USER_ID, req.getRecordDate())
                .isPresent();
        if (exists) {
            throw new IllegalArgumentException(
                    "A weight record for user_id=" + DEFAULT_USER_ID +
                    " on " + req.getRecordDate() + " already exists.");
        }

        WeightRecord record = new WeightRecord();
        record.setUser(user);
        record.setRecordDate(req.getRecordDate());
        record.setWeightKg(req.getWeightKg());
        record.setNote(req.getNote());

        return WeightRecordResponse.from(weightRecordRepository.save(record));
    }

    @Transactional(readOnly = true)
    public List<WeightRecordResponse> getAll() {
        return weightRecordRepository.findByUserIdOrderByRecordDateDesc(DEFAULT_USER_ID)
                .stream()
                .map(WeightRecordResponse::from)
                .toList();
    }

    @Transactional
    public WeightRecordResponse update(Long id, UpdateWeightRecordRequest req) {
        WeightRecord record = weightRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Weight record with id=" + id + " not found."));

        if (!record.getUser().getId().equals(DEFAULT_USER_ID)) {
            throw new IllegalStateException(
                    "Weight record with id=" + id + " not found.");
        }

        if (!record.getRecordDate().equals(req.getRecordDate())) {
            weightRecordRepository
                    .findByUserIdAndRecordDate(DEFAULT_USER_ID, req.getRecordDate())
                    .ifPresent(conflict -> {
                        throw new IllegalArgumentException(
                                "A weight record for user_id=" + DEFAULT_USER_ID +
                                " on " + req.getRecordDate() + " already exists.");
                    });
        }

        record.setRecordDate(req.getRecordDate());
        record.setWeightKg(req.getWeightKg());
        record.setNote(req.getNote());

        return WeightRecordResponse.from(weightRecordRepository.save(record));
    }

    @Transactional
    public void delete(Long id) {
        WeightRecord record = weightRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Weight record with id=" + id + " not found."));

        if (!record.getUser().getId().equals(DEFAULT_USER_ID)) {
            throw new IllegalStateException(
                    "Weight record with id=" + id + " not found.");
        }

        weightRecordRepository.delete(record);
    }
}
