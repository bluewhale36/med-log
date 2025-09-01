package com.bluewhale.medlog.medintakesnapshot.repository;

import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnapshotPolicyRepository extends JpaRepository<MedIntakeSnapshot, Long> {
    List<MedIntakeSnapshot> findAllByAppUserId(Long appUserId);

    List<MedIntakeSnapshot> findAllByMedId(Long medId);
}
