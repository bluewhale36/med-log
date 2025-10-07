package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class MedRegisterDTO {

    private final VisitUuid visitUuid;
    private final AppUserUuid appUserUuid;
    private final String medName;
    private final MedType medType;
    private final Float doseAmount;
    private final DoseUnit doseUnit;
    private final DoseFrequency doseFrequency;
    private final String instruction;
    private final String effect;
    private final String sideEffect;
    private final LocalDate startedOn;
    private final LocalDate endedOn;
}
