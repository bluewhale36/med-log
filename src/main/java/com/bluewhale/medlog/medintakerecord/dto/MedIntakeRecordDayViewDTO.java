package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Getter
@ToString
@EqualsAndHashCode
public class MedIntakeRecordDayViewDTO {

    private final LocalDate stdDate;
    private final Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeRecord;
    private final Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeScheduled;

    private MedIntakeRecordDayViewDTO(
            LocalDate stdDate,
            Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeRecord,
            Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeScheduled) {
        this.stdDate = stdDate;
        this.viewItemDTOListMapForTypeRecord = viewItemDTOListMapForTypeRecord;
        this.viewItemDTOListMapForTypeScheduled = viewItemDTOListMapForTypeScheduled;
    }

    public static MedIntakeRecordDayViewDTO of(
            LocalDate stdDate,
            Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeRecord,
            Map<LocalTime, List<MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeScheduled
    ) {
        if (viewItemDTOListMapForTypeRecord != null && !viewItemDTOListMapForTypeRecord.isEmpty()) {
            if (
                    viewItemDTOListMapForTypeRecord.keySet().stream()
                            .map(viewItemDTOListMapForTypeRecord::get)
                            .flatMap(List::stream)
                            .noneMatch(
                                    dto -> dto.getRecordViewType().equals(RecordViewType.RECORD)
                            )
            ) {
                throw new IllegalArgumentException(
                        "RecordViewType of ItemDTO in ViewItemDTOListMapForTypeRecord must be the type of RECORD."
                );
            }
        }
        if (viewItemDTOListMapForTypeScheduled != null && !viewItemDTOListMapForTypeScheduled.isEmpty()) {
            if (
                    viewItemDTOListMapForTypeScheduled.keySet().stream()
                            .map(viewItemDTOListMapForTypeScheduled::get)
                            .flatMap(List::stream)
                            .noneMatch(
                                    dto -> dto.getRecordViewType().equals(RecordViewType.SCHEDULED)
                            )
            ) {
                throw new IllegalArgumentException(
                        "RecordViewType of ItemDTO in ViewItemDTOListMapForTypeScheduled must be the type of SCHEDULED."
                );
            }
        }
        return new MedIntakeRecordDayViewDTO(stdDate, viewItemDTOListMapForTypeRecord, viewItemDTOListMapForTypeScheduled);
    }

    @RequiredArgsConstructor
    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class MedIntakeRecordViewItemDTO {

        private final RecordViewType recordViewType;

        private final Optional<MedIntakeRecordUuid> mirUuid;

        private final MedUuid medUuid;
        private final String medName;
        private final MedType medType;
        private final Float doseAmount;
        private final DoseUnit doseUnit;
        private final boolean isTaken;

        private final LocalDateTime stdDateTime;

    }
}
