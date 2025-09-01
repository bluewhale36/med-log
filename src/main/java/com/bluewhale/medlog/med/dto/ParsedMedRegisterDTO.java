package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.factory.MedRegisterDTOFactory;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * {@link com.bluewhale.medlog.med.parser.PayloadParser} 과 {@link MedRegisterDTOFactory} 간
 * 정형화된 데이터 교환 시 사용되는 비즈니스 로직 내부 사용의 DTO 클래스.
 */
@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class ParsedMedRegisterDTO {

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
}
