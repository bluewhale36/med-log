package com.bluewhale.medlog.medintakesnapshot.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
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

    public static MedIntakeSnapshotDTO from(MedIntakeSnapshot entity) {
        return MedIntakeSnapshotDTO.builder()
                .medIntakeSnapshotId(entity.getMedIntakeSnapshotId())
                .snapshotDate(entity.getSnapshotDate())
                .appUserUuid(entity.getAppUser().getAppUserUuid())
                .medUuid(entity.getMed().getMedUuid())
                .shouldTake(entity.isShouldTake())
                .isTaken(entity.isTaken())
                .estimatedDoseTime(entity.getEstimatedDoseTime())
                .policyReason(entity.getPolicyReason())
                .evaluatedAt(entity.getEvaluatedAt())
                .build();
    }
}
