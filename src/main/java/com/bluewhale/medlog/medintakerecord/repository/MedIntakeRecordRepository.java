package com.bluewhale.medlog.medintakerecord.repository;

import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedIntakeRecordRepository extends JpaRepository<MedIntakeRecord, Long> {
    Optional<MedIntakeRecord> findByMedIntakeRecordUuid(MedIntakeRecordUuid medIntakeRecordUuid);

    List<MedIntakeRecord> findByMedId(Long medId);

    @Query("select m.estimatedDoseTime from MedIntakeRecord m where m.medId = :medId")
    List<LocalDate> findAllRecordedDateByMedID(Long medId);
}
