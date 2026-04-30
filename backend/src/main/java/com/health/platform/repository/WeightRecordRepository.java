package com.health.platform.repository;

import com.health.platform.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    List<WeightRecord> findByUserIdOrderByRecordDateAsc(Long userId);
    Optional<WeightRecord> findFirstByUserIdOrderByRecordDateAsc(Long userId);
    Optional<WeightRecord> findFirstByUserIdOrderByRecordDateDesc(Long userId);
    Optional<WeightRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
    List<WeightRecord> findByUserIdAndRecordDateGreaterThanEqualOrderByRecordDateAsc(Long userId, LocalDate startDate);
    List<WeightRecord> findByUserIdAndRecordDateLessThanEqualOrderByRecordDateAsc(Long userId, LocalDate endDate);
    List<WeightRecord> findByUserIdAndRecordDateBetweenOrderByRecordDateAsc(Long userId, LocalDate startDate, LocalDate endDate);
}
