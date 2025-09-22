package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDetailViewModel;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
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
                        generateDetailFrequencySentence(fetchedEntity)
                )
                .build();
    }

    private static String generateDetailFrequencySentence(Med fetchedEntity) {
        return null;
    }

}
