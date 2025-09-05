package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
public class MedDTO {

    private final MedUuid medUuid;
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

    public static MedDTO from(Med entity) {
        return new MedDTO(
                entity.getMedUuid(),
                entity.getHospitalVisitRecord() != null ?  entity.getHospitalVisitRecord().getVisitUuid() : null,
                entity.getAppUser().getAppUserUuid(),
                entity.getMedName(),
                entity.getMedType(),
                entity.getDoseAmount(),
                entity.getDoseUnit(),
                entity.getDoseFrequency(),
                entity.getInstruction(),
                entity.getEffect(),
                entity.getSideEffect(),
                entity.getStartedOn(),
                entity.getEndedOn()
        );
    }
}