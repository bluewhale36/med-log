package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import lombok.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class MedModifyDTO {

    private final AppUserUuid appUserUuid;
    private final MedUuid medUuid;
    private final DoseFrequency doseFrequency;
    private final String instruction;
    private final String effect;
    private final String sideEffect;
    private final LocalDate startedOn;
    private final LocalDate endedOn;

}
