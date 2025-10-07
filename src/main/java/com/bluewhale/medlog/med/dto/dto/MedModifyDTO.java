package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import lombok.*;

import java.time.LocalDate;

@Builder
public record MedModifyDTO(
        AppUserUuid appUserUuid,
        MedUuid medUuid,
        DoseFrequency doseFrequency,
        String instruction,
        String effect,
        String sideEffect,
        LocalDate startedOn,
        LocalDate endedOn
) {

}
