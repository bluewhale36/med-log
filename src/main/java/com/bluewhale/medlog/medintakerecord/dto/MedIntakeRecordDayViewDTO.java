package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class MedIntakeRecordDayViewDTO {

    private final LocalDate referenceDate;
    private final Map<LocalTime, List<ViewItemTypeRecordDTO>> viewItemDTOListMapForTypeRecord;
    private final Map<LocalTime, List<ViewItemTypeScheduledDTO>> viewItemDTOListMapForTypeScheduled;

    public static MedIntakeRecordDayViewDTO of(
            LocalDate referenceDate,
            Map<LocalTime, List<ViewItemTypeRecordDTO>> viewItemDTOListMapForTypeRecord,
            Map<LocalTime, List<ViewItemTypeScheduledDTO>> viewItemDTOListMapForTypeScheduled
    ) {
        return new MedIntakeRecordDayViewDTO(referenceDate, viewItemDTOListMapForTypeRecord, viewItemDTOListMapForTypeScheduled);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ViewItemTypeRecordDTO {

        private final RecordViewType recordViewType = RecordViewType.RECORD;

        // 약 정보 전달 목적
        private final MedDTO medDTO;
        // 복용 기록 정보 전달 목적
        private final MedIntakeRecordDTO medIntakeRecordDTO;

        // 기준 시점 그룹화 목적
        private final LocalDateTime referenceDateTime;

        public static ViewItemTypeRecordDTO of(MedIntakeRecord entity, LocalDateTime referenceDateTime) {
            if (entity == null) {
                throw new IllegalArgumentException("MedIntakeRecord entity cannot be null");
            }
            if (referenceDateTime == null) {
                throw new IllegalArgumentException("ReferenceDateTime cannot be null");
            }

            Med medEntity = entity.getMed();
            if (medEntity == null) {
                throw new IllegalArgumentException("Med entity in MedIntakeRecord cannot be null");
            }

            return ViewItemTypeRecordDTO.builder()
                    .medDTO(MedDTO.from(medEntity))
                    .medIntakeRecordDTO(MedIntakeRecordDTO.from(entity))
                    .referenceDateTime(referenceDateTime)
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ViewItemTypeScheduledDTO {
        private final RecordViewType recordViewType = RecordViewType.SCHEDULED;

        private final MedUuid medUuid;
        private final String medName;
        private final MedType medType;
        private final Float doseAmount;
        private final DoseUnit doseUnit;
        private final Integer doseCount;
        private final LocalTime estimatedDoseTime;

        private final LocalDateTime referenceDateTime;

        public static ViewItemTypeScheduledDTO of(Med entity, Integer doseCount, LocalDateTime referenceDateTime) {
            if (entity == null) {
                throw new IllegalArgumentException("Med entity cannot be null");
            }
            if (referenceDateTime == null) {
                throw new IllegalArgumentException("ReferenceDateTime cannot be null");
            }

            return ViewItemTypeScheduledDTO.builder()
                    .medUuid(entity.getMedUuid())
                    .medName(entity.getMedName())
                    .medType(entity.getMedType())
                    .doseAmount(entity.getDoseAmount())
                    .doseUnit(entity.getDoseUnit())
                    .doseCount(doseCount)
                    .estimatedDoseTime(referenceDateTime.toLocalTime())
                    .referenceDateTime(referenceDateTime)
                    .build();
        }
    }
}
