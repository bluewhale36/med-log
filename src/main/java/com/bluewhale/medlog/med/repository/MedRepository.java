package com.bluewhale.medlog.med.repository;

import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedViewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedRepository extends JpaRepository<Med, Long>, IdentifierRoutableRepository<Med, Long, MedUuid> {

    Optional<Med> findByMedUuid(MedUuid uuid);

    @Query("select m from Med m where m.appUser.appUserId = :appUserId")
    List<Med> findAllByAppUserId(Long appUserId);

    @Modifying
    @Query("DELETE FROM Med m where m.medId = :medId")
    void hardDeleteByMedId(Long medId);

    @Override
    @Query("SELECT m.medId FROM Med m WHERE m.medUuid = :uuid")
    Optional<Long> findIdByUuid(MedUuid uuid);

    @Override
    @Query("SELECT m.medUuid FROM Med m WHERE m.medId = :id")
    Optional<MedUuid> findUuidById(Long id);

    @Query(
            """
            SELECT
            m.medId AS medId, m.medUuid AS medUuid,
            m.medName AS medName, m.medType AS medType,
            m.doseAmount AS doseAmount, m.doseUnit AS doseUnit, m.doseFrequency AS doseFrequency,
            m.instruction AS instruction, m.effect AS effect, m.sideEffect AS sideEffect,
            m.startedOn AS startedOn, m.endedOn AS endedOn
            FROM Med m
            WHERE m.medId IN :medIdList
            """
    )
    List<MedViewProjection> findAllProjectionByMedIdIn(List<Long> medIdList);

}
