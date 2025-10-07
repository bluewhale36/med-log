package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public record MedDTO(
        MedUuid medUuid,
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

    public static MedDTO from(Med entity) {
        return new MedDTO(
                entity.getMedUuid(),
                entity.getHospitalVisitRecord() != null ? entity.getHospitalVisitRecord().getVisitUuid() : null,
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