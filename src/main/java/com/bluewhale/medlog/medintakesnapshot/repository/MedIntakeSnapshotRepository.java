package com.bluewhale.medlog.medintakesnapshot.repository;

import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedIntakeSnapshotRepository extends JpaRepository<MedIntakeSnapshot, Long> {

    @Query("select s from MedIntakeSnapshot s where s.med.medId = :medId")
    List<MedIntakeSnapshot> findAllByMedId(Long medId);

    @Modifying
    void deleteByMed_MedId(Long medMedId);

    void deleteByAppUser_AppUserId(Long appUserAppUserId);

    @Query("SELECT s FROM MedIntakeSnapshot s WHERE s.med.medId = :medId AND s.estimatedDoseTime = :estimatedDoseTime")
    MedIntakeSnapshot findByMedIdAndEstimatedDoseTime(Long medId, LocalDateTime estimatedDoseTime);

    void deleteAllByMed_MedId(Long medMedId);

    List<MedIntakeSnapshot> findAllByAppUser_AppUserIdAndEstimatedDoseTimeIsBetween(
            Long appUserAppUserId, LocalDateTime startDateTimeInclude, LocalDateTime endDateTimeExclude
    );
}
