package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
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
