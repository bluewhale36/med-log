package com.bluewhale.medlog.med.repository;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedRepository extends JpaRepository<Med, Long> {

    Optional<Med> findByMedUuid(MedUuid uuid);

    @Query("select m from Med m where m.appUser.appUserId = :appUserId")
    List<Med> findAllByAppUserId(Long appUserId);

    @Query("select m.medId from Med m where m.medUuid = :medUuid")
    Optional<Long> findIdByMedUuid(MedUuid medUuid);

    @Modifying
    @Query("DELETE FROM Med m where m.medId = :medId")
    void hardDeleteByMedId(Long medId);
}
