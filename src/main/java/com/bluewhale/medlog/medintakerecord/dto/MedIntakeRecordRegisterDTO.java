package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class MedIntakeRecordRegisterDTO {

    private final MedUuid medUuid;
    private final AppUserUuid appUserUuid;

    /*
        true : 복용함
        false : 건너뜀
     */
    private final boolean isTaken;

    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;
}
