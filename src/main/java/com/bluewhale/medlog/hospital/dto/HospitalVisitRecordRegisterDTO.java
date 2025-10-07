package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

public record HospitalVisitRecordRegisterDTO(
        AppUserUuid appUserUuid,
        String hospitalName,
        LocalDateTime consultedAt,
        String chiefSymptom,
        String diagnosis,
        String physicianName
) {

}
