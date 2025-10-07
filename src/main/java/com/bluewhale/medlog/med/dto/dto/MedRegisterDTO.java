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

@Builder
public record MedRegisterDTO(
        VisitUuid visitUuid,
        AppUserUuid appUserUuid,
        String medName,
        MedType medType,
        Float doseAmount,
        DoseUnit doseUnit,
        DoseFrequency doseFrequency,
        String instruction,
        String effect,
        String sideEffect,
        LocalDate startedOn,
        LocalDate endedOn
) {

}
