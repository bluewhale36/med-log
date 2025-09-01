package com.bluewhale.medlog.medintakesnapshot.token;

import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class PolicyRequestMedIntakeToken {

    private final Long medIntakeRecordId;
    private final Long medId;
    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;
}
