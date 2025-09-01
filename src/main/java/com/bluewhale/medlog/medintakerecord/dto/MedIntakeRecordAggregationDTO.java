package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class MedIntakeRecordAggregationDTO {

    private final Long medIntakeRecordId;
    private final MedIntakeRecordUuid medIntakeRecordUuid;

    private final Long medId;
    private final MedUuid medUuid;

    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;


}
