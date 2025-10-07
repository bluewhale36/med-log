package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

// Jackson Library 와 호환을 위해 Record Class 사용.
@Builder
public record MedIntakeRecordDTO(
        MedIntakeRecordUuid medIntakeRecordUuid,
        MedUuid medUuid,
        @JsonGetter("isTaken") boolean isTaken,
        LocalDateTime estimatedDoseTime,
        LocalDateTime takenAt
) {
    public static MedIntakeRecordDTO from(MedIntakeRecord entity) {
        return MedIntakeRecordDTO.builder()
                .medIntakeRecordUuid(entity.getMedIntakeRecordUuid())
                .medUuid(entity.getMed().getMedUuid())
                .isTaken(entity.isTaken())
                .estimatedDoseTime(entity.getEstimatedDoseTime())
                .takenAt(entity.getTakenAt())
                .build();
    }
}
