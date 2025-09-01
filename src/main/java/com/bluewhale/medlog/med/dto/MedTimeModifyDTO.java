package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MedTimeModifyDTO {

    private final AppUserUuid appUserUuid;
    private final MedUuid medUuid;
    private final DoseFrequency doseFrequency;
    private final LocalDate startedOn;
    private final LocalDate endedOn;

}
