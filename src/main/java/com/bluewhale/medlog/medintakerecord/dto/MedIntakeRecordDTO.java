package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class MedIntakeRecordDTO {

    private final MedIntakeRecordUuid medIntakeRecordUuid;
    private final MedUuid medUuid;
    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;
}
