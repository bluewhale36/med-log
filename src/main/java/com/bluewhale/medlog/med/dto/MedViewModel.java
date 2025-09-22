package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class MedViewModel {

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

    private final String humanReadableDoseSentenceShort;
    private final String humanReadableDoseSentenceLong;

    public static MedViewModel from(MedDTO dto) {
        return MedViewModel.builder()
                .medUuid(dto.getMedUuid())
                .visitUuid(dto.getVisitUuid())
                .appUserUuid(dto.getAppUserUuid())
                .medName(dto.getMedName())
                .medType(dto.getMedType())
                .doseAmount(dto.getDoseAmount())
                .doseUnit(dto.getDoseUnit())
                .doseFrequency(dto.getDoseFrequency())
                .instruction(dto.getInstruction())
                .effect(dto.getEffect())
                .sideEffect(dto.getSideEffect())
                .startedOn(dto.getStartedOn())
                .endedOn(dto.getEndedOn())
                .humanReadableDoseSentenceShort(buildDoseSentenceShort(dto))
                .humanReadableDoseSentenceLong(buildDoseSentenceLong(dto))
                .build();
    }

    private static String buildDoseSentenceShort(MedDTO dto) {
        return dto.getDoseFrequency().getDoseFrequencyDetail().getHumanReadableFirstSentence();
    }

    private static String buildDoseSentenceLong(MedDTO dto) {
        return "";
    }



}
