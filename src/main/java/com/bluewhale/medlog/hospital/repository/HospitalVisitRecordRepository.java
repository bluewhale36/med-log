package com.bluewhale.medlog.hospital.repository;

import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalVisitRecordRepository extends JpaRepository<HospitalVisitRecord, Long> {

    Optional<HospitalVisitRecord> findByVisitUuid(VisitUuid visitUuid);

    List<HospitalVisitRecord> findAllByAppUserId(Long appUserId);
}
