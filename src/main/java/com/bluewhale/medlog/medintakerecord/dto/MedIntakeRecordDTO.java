package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class MedIntakeRecordDTO {

    private final MedIntakeRecordUuid medIntakeRecordUuid;
    private final MedUuid medUuid;
    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;

    @JsonCreator
    public MedIntakeRecordDTO(
            @JsonProperty("medIntakeRecordUuid") MedIntakeRecordUuid medIntakeRecordUuid,
            @JsonProperty("medUuid") MedUuid medUuid,
            @JsonProperty("isTaken") boolean isTaken,
            @JsonProperty("estimatedDoseTime") LocalDateTime estimatedDoseTime,
            @JsonProperty("takenAt") LocalDateTime takenAt
    ) {
        this.medIntakeRecordUuid = medIntakeRecordUuid;
        this.medUuid = medUuid;
        this.isTaken = isTaken;
        this.estimatedDoseTime = estimatedDoseTime;
        this.takenAt = takenAt;
    }

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
