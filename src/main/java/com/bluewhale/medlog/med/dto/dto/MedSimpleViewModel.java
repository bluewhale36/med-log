package com.bluewhale.medlog.med.dto.dto;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MedSimpleViewModel {

    private final MedUuid medUuid;
    private final String medName;
    private final MedType medType;
    private final Float doseAmount;
    private final DoseUnit doseUnit;
    private final DoseFrequency doseFrequency;
    private final LocalDate startedOn;
    private final LocalDate endedOn;

    private final String frequencySentence;

    @JsonCreator
    public MedSimpleViewModel(
            @JsonProperty("medUuid") MedUuid medUuid,
            @JsonProperty("medName") String medName,
            @JsonProperty("medType") MedType medType,
            @JsonProperty("doseAmount") Float doseAmount,
            @JsonProperty("doseUnit") DoseUnit doseUnit,
            @JsonProperty("doseFrequency") DoseFrequency doseFrequency,

            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateDeserializer.class)
            @JsonProperty("startedOn")
            LocalDate startedOn,

            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateDeserializer.class)
            @JsonProperty("endedOn")
            LocalDate endedOn
    ) {
        this.medUuid = medUuid;
        this.medName = medName;
        this.medType = medType;
        this.doseAmount = doseAmount;
        this.doseUnit = doseUnit;
        this.doseFrequency = doseFrequency;
        this.startedOn = startedOn;
        this.endedOn = endedOn;
        this.frequencySentence =
                generateFrequencySimpleSentence(doseFrequency.getDoseFrequencyType(), medType);
    }

    public static MedSimpleViewModel from(Med unfetchedEntity) {
        return MedSimpleViewModel.builder()
                .medUuid(unfetchedEntity.getMedUuid())
                .medName(unfetchedEntity.getMedName())
                .medType(unfetchedEntity.getMedType())
                .doseAmount(unfetchedEntity.getDoseAmount())
                .doseUnit(unfetchedEntity.getDoseUnit())
                .doseFrequency(unfetchedEntity.getDoseFrequency())
                .startedOn(unfetchedEntity.getStartedOn())
                .endedOn(unfetchedEntity.getEndedOn())
                .frequencySentence(
                        generateFrequencySimpleSentence(
                                unfetchedEntity.getDoseFrequency().getDoseFrequencyType(), unfetchedEntity.getMedType()
                        )
                )
                .build();
    }

    private static String generateFrequencySimpleSentence(DoseFrequencyType frequencyType, MedType medType) {
        /*
            DoseFrequencyType, MedType 필요.
         */
        return String.format("%s %s", frequencyType.getTitle(), medType.getDoseActionInVerb());
    }
}
