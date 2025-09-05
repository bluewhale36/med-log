package com.bluewhale.medlog.medintakesnapshot.service;

import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotAggregationDTO;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeSnapshotAggregationService {

    private final SnapshotPolicyRepository spRepo;

    public List<MedIntakeSnapshotAggregationDTO> getMisAggDTOListByMedId(Long medId) {
        List<MedIntakeSnapshot> entityList = spRepo.findAllByMedId(medId);
        return entityList.stream().map(this::toAggregationDTO).toList();
    }

    private MedIntakeSnapshotAggregationDTO toAggregationDTO(MedIntakeSnapshot entity) {
        return MedIntakeSnapshotAggregationDTO.builder()
                .medIntakeSnapshotId(entity.getMedIntakeSnapshotId())
                .snapshotDate(entity.getSnapshotDate())
                .appUserId(entity.getAppUser().getAppUserId())
                .appUserUuid(entity.getAppUser().getAppUserUuid())
                .medId(entity.getMed().getMedId())
                .medUuid(entity.getMed().getMedUuid())
                .shouldTake(entity.isShouldTake())
                .isTaken(entity.isTaken())
                .estimatedDoseTime(entity.getEstimatedDoseTime())
                .policyReason(entity.getPolicyReason())
                .evaluatedAt(entity.getEvaluatedAt())
                .build();
    }
}
