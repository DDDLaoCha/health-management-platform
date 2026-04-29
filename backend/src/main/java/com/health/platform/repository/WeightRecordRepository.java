package com.health.platform.repository;

import com.health.platform.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    List<WeightRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    Optional<WeightRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
