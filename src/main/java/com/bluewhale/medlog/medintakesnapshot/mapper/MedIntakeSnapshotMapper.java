package com.bluewhale.medlog.medintakesnapshot.mapper;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotAggregationDTO;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotDTO;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedIntakeSnapshotMapper {

    public MedIntakeSnapshot toEntity(PolicyEvaluateResult peResult) {
        return MedIntakeSnapshot.builder()
                .snapshotDate(peResult.getStdDate())
                .appUserId(peResult.getAppUserId())
                .medId(peResult.getMedId())
                .shouldTake(peResult.isShouldTake())
                .isTaken(false)
                .estimatedDoseTime(peResult.getEstimatedDoseTime())
                .policyReason(peResult.getReason())
                .evaluatedAt(peResult.getEvaluatedAt())
                .build();
    }

    public List<MedIntakeSnapshot> toEntityList(List<PolicyEvaluateResult> peResultList) {
        if (peResultList == null || peResultList.isEmpty()) {
            return new ArrayList<>();
        }
        return peResultList.stream().map(this::toEntity).toList();
    }

    public MedIntakeSnapshotDTO toDTO(MedIntakeSnapshot entity, AppUserUuid appUserUuid, MedUuid medUuid) {
        return MedIntakeSnapshotDTO.builder()
                .medIntakeSnapshotId(entity.getMedIntakeSnapshotId())
                .snapshotDate(entity.getSnapshotDate())
                .appUserUuid(appUserUuid)
                .medUuid(medUuid)
                .shouldTake(entity.isShouldTake())
                .isTaken(entity.isTaken())
                .estimatedDoseTime(entity.getEstimatedDoseTime())
                .policyReason(entity.getPolicyReason())
                .evaluatedAt(entity.getEvaluatedAt())
                .build();
    }

    public PolicyEvaluateResult toResultToken(MedIntakeSnapshot misEntity) {
        return new PolicyEvaluateResult(
                misEntity.getAppUserId(),
                misEntity.getMedId(),
                misEntity.isShouldTake(),
                misEntity.getSnapshotDate(),
                misEntity.getEstimatedDoseTime(),
                misEntity.getPolicyReason(),
                misEntity.getEvaluatedAt()
        );
    }

    public List<PolicyEvaluateResult> toResultTokenList(List<MedIntakeSnapshot> misEntityList) {
        if (misEntityList == null || misEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return misEntityList.stream().map(this::toResultToken).toList();
    }

    public MedIntakeSnapshotAggregationDTO toAggregationDTO(MedIntakeSnapshot misEntity, AppUserUuid appUserUuid, MedUuid medUuid) {
        return MedIntakeSnapshotAggregationDTO.builder()
                .medIntakeSnapshotId(misEntity.getMedIntakeSnapshotId())
                .snapshotDate(misEntity.getSnapshotDate())
                .appUserId(misEntity.getAppUserId())
                .appUserUuid(appUserUuid)
                .medId(misEntity.getMedId())
                .medUuid(medUuid)
                .shouldTake(misEntity.isShouldTake())
                .isTaken(misEntity.isTaken())
                .estimatedDoseTime(misEntity.getEstimatedDoseTime())
                .policyReason(misEntity.getPolicyReason())
                .evaluatedAt(misEntity.getEvaluatedAt())
                .build();
    }

}
