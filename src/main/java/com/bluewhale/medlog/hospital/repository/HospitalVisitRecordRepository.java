package com.bluewhale.medlog.hospital.repository;

import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalVisitRecordRepository extends JpaRepository<HospitalVisitRecord, Long> {

    Optional<HospitalVisitRecord> findByVisitUuid(VisitUuid visitUuid);

    @Query("select hvr from HospitalVisitRecord hvr where hvr.appUser.appUserId = :appUserId")
    List<HospitalVisitRecord> findAllByAppUserId(Long appUserId);

    Optional<Long> findIdByVisitUuid(VisitUuid visitUuid);
}
