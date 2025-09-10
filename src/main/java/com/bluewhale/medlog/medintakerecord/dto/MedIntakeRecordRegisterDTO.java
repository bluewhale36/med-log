package com.bluewhale.medlog.medintakerecord.dto;

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

    /*
        True : 복용함
        False : 건너뜀
        Null : 기록 없음 -> DB 에 INSERT 하지 않음
     */
    private final Boolean isTaken;

    private final LocalDateTime estimatedDoseTime;
    private final LocalDateTime takenAt;
}
