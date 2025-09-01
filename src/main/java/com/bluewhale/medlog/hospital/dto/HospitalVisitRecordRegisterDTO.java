package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class HospitalVisitRecordRegisterDTO {

    private final AppUserUuid appUserUuid;
    private final String hospitalName;
    private final LocalDateTime consultedAt;
    private final String chiefSymptom;
    private final String diagnosis;
    private final String physicianName;
}
