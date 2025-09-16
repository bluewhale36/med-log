package com.bluewhale.medlog.medintakerecord.repository;

import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedIntakeRecordRepository extends JpaRepository<MedIntakeRecord, Long>, IdentifierRoutableRepository<MedIntakeRecord, Long, MedIntakeRecordUuid> {
    Optional<MedIntakeRecord> findByMedIntakeRecordUuid(MedIntakeRecordUuid medIntakeRecordUuid);

    @Query("select r from MedIntakeRecord r where r.med.medId = :medId")
    List<MedIntakeRecord> findByMedId(Long medId);

    @Override
    @Query("SELECT mir.medIntakeRecordId FROM MedIntakeRecord mir WHERE mir.medIntakeRecordUuid = :uuid")
    Optional<Long> findIdByUuid(MedIntakeRecordUuid uuid);

    @Override
    @Query("SELECT mir.medIntakeRecordUuid FROM MedIntakeRecord mir WHERE mir.medIntakeRecordId = :id")
    Optional<MedIntakeRecordUuid> findUuidById(Long id);

    @Query("SELECT r FROM MedIntakeRecord r WHERE r.med.medId IN :medIdList AND r.estimatedDoseTime >= :startDateTimeInclude AND r.estimatedDoseTime < :endDateTimeExclude ORDER BY r.estimatedDoseTime")
    List<MedIntakeRecord> findAllByMed_MedIdAndEstimatedDoseTimeInRange(
            List<Long> medIdList, LocalDateTime startDateTimeInclude, LocalDateTime endDateTimeExclude
    );
}
