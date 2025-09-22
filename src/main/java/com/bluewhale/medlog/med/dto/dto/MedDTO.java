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
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
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

    @JsonCreator
    public MedDTO(
            @JsonProperty("medUuid") MedUuid medUuid,
            @JsonProperty("visitUuid") VisitUuid visitUuid,
            @JsonProperty("appUserUuid") AppUserUuid appUserUuid,
            @JsonProperty("medName") String medName,
            @JsonProperty("medType") MedType medType,
            @JsonProperty("doseAmount") Float doseAmount,
            @JsonProperty("doseUnit") DoseUnit doseUnit,
            @JsonProperty("doseFrequency") DoseFrequency doseFrequency,
            @JsonProperty("instruction") String instruction,
            @JsonProperty("effect") String effect,
            @JsonProperty("sideEffect") String sideEffect,

            @JsonProperty("startedOn")
            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateDeserializer.class)
            LocalDate startedOn,

            @JsonProperty("endedOn")
            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateDeserializer.class)
            LocalDate endedOn
    ) {
        this.medUuid = medUuid;
        this.visitUuid = visitUuid;
        this.appUserUuid = appUserUuid;
        this.medName = medName;
        this.medType = medType;
        this.doseAmount = doseAmount;
        this.doseUnit = doseUnit;
        this.doseFrequency = doseFrequency;
        this.instruction = instruction;
        this.effect = effect;
        this.sideEffect = sideEffect;
        this.startedOn = startedOn;
        this.endedOn = endedOn;
    }


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