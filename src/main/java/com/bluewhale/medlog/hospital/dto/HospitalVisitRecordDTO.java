package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class HospitalVisitRecordDTO {

    private final VisitUuid visitUuid;
    private final AppUserUuid appUserUuid;
    private final String hospitalName;
    private final LocalDateTime consultedAt;
    private final String chiefSymptom;
    private final String diagnosis;
    private final String physicianName;

}
