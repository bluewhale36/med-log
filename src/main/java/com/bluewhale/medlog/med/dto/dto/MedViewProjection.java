package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;

import java.time.LocalDate;

public interface MedViewProjection {
    Long getMedId();
    MedUuid getMedUuid();

    String getMedName();
    MedType getMedType();
    Float getDoseAmount();
    DoseUnit getDoseUnit();
    DoseFrequency getDoseFrequency();

    String getInstruction();
    String getEffect();
    String getSideEffect();

    LocalDate getStartedOn();
    LocalDate getEndedOn();
}
