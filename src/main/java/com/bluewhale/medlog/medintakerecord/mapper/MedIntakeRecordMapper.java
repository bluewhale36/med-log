package com.bluewhale.medlog.medintakerecord.mapper;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import org.springframework.stereotype.Component;

@Component
public class MedIntakeRecordMapper {

    public MedIntakeRecordDTO toDTO(MedIntakeRecord mirEntity, MedUuid medUuid) {
        return MedIntakeRecordDTO.builder()
                .medIntakeRecordUuid(mirEntity.getMedIntakeRecordUuid())
                .medUuid(medUuid)
                .isTaken(mirEntity.isTaken())
                .estimatedDoseTime(mirEntity.getEstimatedDoseTime())
                .takenAt(mirEntity.getTakenAt())
                .build();
    }

    public MedIntakeRecordAggregationDTO toFullDTO(MedIntakeRecord mirEntity, MedUuid medUuid) {
        return MedIntakeRecordAggregationDTO.builder()
                .medIntakeRecordId(mirEntity.getMedIntakeRecordId())
                .medIntakeRecordUuid(mirEntity.getMedIntakeRecordUuid())
                .medId(mirEntity.getMedId())
                .medUuid(medUuid)
                .isTaken(mirEntity.isTaken())
                .estimatedDoseTime(mirEntity.getEstimatedDoseTime())
                .takenAt(mirEntity.getTakenAt())
                .build();
    }
}
