package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @param isTaken true : 복용함
 *                false : 건너뜀
 */
@Builder
public record MedIntakeRecordRegisterDTO(
        MedUuid medUuid,
        AppUserUuid appUserUuid,
        @JsonProperty("isTaken") boolean isTaken,
        LocalDateTime estimatedDoseTime,
        LocalDateTime takenAt
) {
}
