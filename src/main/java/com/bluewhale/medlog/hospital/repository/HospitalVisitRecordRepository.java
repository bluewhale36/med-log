package com.bluewhale.medlog.hospital.repository;

import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalVisitRecordRepository extends JpaRepository<HospitalVisitRecord, Long>, IdentifierRoutableRepository<HospitalVisitRecord, Long, VisitUuid> {

    @Query("select hvr from HospitalVisitRecord hvr where hvr.appUser.appUserId = :appUserId")
    List<HospitalVisitRecord> findAllByAppUserId(Long appUserId);

    @Override
    @Query("SELECT hvr.visitId FROM HospitalVisitRecord hvr WHERE hvr.visitUuid = :uuid")
    Optional<Long> findIdByUuid(VisitUuid uuid);

    @Override
    @Query("SELECT hvr.visitUuid FROM HospitalVisitRecord hvr WHERE hvr.visitId = :id")
    Optional<VisitUuid> findUuidById(Long id);
}
