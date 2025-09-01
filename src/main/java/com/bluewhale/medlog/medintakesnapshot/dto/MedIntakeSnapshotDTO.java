package com.bluewhale.medlog.medintakesnapshot.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class MedIntakeSnapshotDTO {

    private final Long medIntakeSnapshotId;
    private final LocalDate snapshotDate;
    private final AppUserUuid appUserUuid;
    private final MedUuid medUuid;
    private final boolean shouldTake;
    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;
    private final PolicyEvaluateTracer policyReason;
    private final LocalDateTime evaluatedAt;

}
