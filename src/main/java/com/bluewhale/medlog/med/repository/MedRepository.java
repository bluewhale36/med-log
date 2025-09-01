package com.bluewhale.medlog.med.repository;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedRepository extends JpaRepository<Med, Long> {

    Optional<Med> findByMedUuid(MedUuid uuid);

    List<Med> findAllByAppUserId(Long appUserId);
}
