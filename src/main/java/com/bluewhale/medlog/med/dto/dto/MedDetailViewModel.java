package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDetailViewModel;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.detail.AbstractDoseFrequencyDetail;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MedDetailViewModel {

    private final MedUuid medUuid;
    private final HospitalVisitRecordDetailViewModel hospitalVisitRecordDetailViewModel;
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

    private final String frequencySentence;

    @JsonCreator
    public MedDetailViewModel(
            @JsonProperty("medUuid") MedUuid medUuid,

            @JsonProperty("hospitalVisitRecordDetailViewModel")
            HospitalVisitRecordDetailViewModel hospitalVisitRecordDetailViewModel,

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
        this.hospitalVisitRecordDetailViewModel = hospitalVisitRecordDetailViewModel;
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
        this.frequencySentence = generateDetailFrequencySentence(doseFrequency.getDoseFrequencyDetail(), medType);
    }

    public static MedDetailViewModel from(Med fetchedEntity) {
        HospitalVisitRecord visitEntity = fetchedEntity.getHospitalVisitRecord();

        return MedDetailViewModel.builder()
                .medUuid(fetchedEntity.getMedUuid())
                .hospitalVisitRecordDetailViewModel(
                        visitEntity != null ?
                        HospitalVisitRecordDetailViewModel.from(visitEntity) :
                        null
                )
                .appUserUuid(fetchedEntity.getAppUser().getAppUserUuid())
                .medName(fetchedEntity.getMedName())
                .medType(fetchedEntity.getMedType())
                .doseAmount(fetchedEntity.getDoseAmount())
                .doseUnit(fetchedEntity.getDoseUnit())
                .doseFrequency(fetchedEntity.getDoseFrequency())
                .instruction(fetchedEntity.getInstruction())
                .effect(fetchedEntity.getEffect())
                .sideEffect(fetchedEntity.getSideEffect())
                .startedOn(fetchedEntity.getStartedOn())
                .endedOn(fetchedEntity.getEndedOn())
                .frequencySentence(
                        generateDetailFrequencySentence(
                                fetchedEntity.getDoseFrequency().getDoseFrequencyDetail(),
                                fetchedEntity.getMedType()
                        )
                )
                .build();
    }

    private static String generateDetailFrequencySentence(AbstractDoseFrequencyDetail frequencyDetail, MedType medType) {
        return frequencyDetail.generateDetailFrequencySentence(medType);
    }

}
