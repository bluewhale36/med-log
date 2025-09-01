package com.bluewhale.medlog.med.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class MedAggregationDTO {

    private final Long medId;
    private final MedUuid medUuid;

    private final Long visitId;
    private final VisitUuid visitUuid;

    private final Long appUserId;
    private final AppUserUuid appUserUuid;

    private final String medName;
    private final MedType medType;

    private final Float doseAmount;
    private final DoseUnit doseUnit;
    private final DoseFrequency doseFrequency;
    private final String effect;
    private final String sideEffect;
    private final LocalDate startedOn;
    private final LocalDate endedOn;

    private final List<MedIntakeRecordAggregationDTO> mirAggrDTOList;

    public static MedAggregationDTO of(
            Long medId, MedUuid medUuid,
            Long visitId, VisitUuid visitUuid,
            Long appUserId, AppUserUuid appUserUuid,
            String medName, MedType medType, Float doseAmount, DoseUnit doseUnit, DoseFrequency doseFrequency,
            String effect, String sideEffect, LocalDate startedOn, LocalDate endedOn,
            List<MedIntakeRecordAggregationDTO> mirAggrDTOList
    ) {
        if (medId == null || medUuid == null) {
            throw new IllegalArgumentException("MedId and MedUuid must not be null");
        }
        if (appUserId == null || appUserUuid == null) {
            throw new IllegalArgumentException("AppUserId and AppUserUuid must not be null");
        }
        if (mirAggrDTOList != null && !mirAggrDTOList.isEmpty()) {
            if (
                    mirAggrDTOList.stream().noneMatch(mir -> mir.getMedId().equals(medId))
            ) {
                throw new IllegalArgumentException(String.format("MedIntakeRecordFullDTO must be the record of this medId: %d", medId));
            }
        }
        return new MedAggregationDTO(
                medId, medUuid, visitId, visitUuid, appUserId, appUserUuid,
                medName, medType, doseAmount, doseUnit, doseFrequency,
                effect, sideEffect, startedOn, endedOn, mirAggrDTOList
        );
    }

}
