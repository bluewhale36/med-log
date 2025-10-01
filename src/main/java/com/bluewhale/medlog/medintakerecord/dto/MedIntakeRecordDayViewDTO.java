package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedDTO;
import com.bluewhale.medlog.med.dto.dto.MedViewProjection;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Getter
@ToString
@EqualsAndHashCode
public class MedIntakeRecordDayViewDTO implements Serializable {

    private final LocalDate referenceDate;
    private final Map<LocalTime, List<ViewItemTypeRecordDTO>> viewItemDTOListMapForTypeRecord;
    private final Map<LocalTime, List<ViewItemTypeScheduledDTO>> viewItemDTOListMapForTypeScheduled;

    @JsonCreator
    public MedIntakeRecordDayViewDTO(

            @JsonProperty("referenceDate")
            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateDeserializer.class)
            LocalDate referenceDate,

            @JsonProperty("viewItemDTOListMapForTypeRecord")
            Map<LocalTime, List<ViewItemTypeRecordDTO>> viewItemDTOListMapForTypeRecord,

            @JsonProperty("viewItemDTOListMapForTypeScheduled")
            Map<LocalTime, List<ViewItemTypeScheduledDTO>> viewItemDTOListMapForTypeScheduled
    ) {
        this.referenceDate = referenceDate;
        this.viewItemDTOListMapForTypeRecord = viewItemDTOListMapForTypeRecord;
        this.viewItemDTOListMapForTypeScheduled = viewItemDTOListMapForTypeScheduled;
    }


    public static MedIntakeRecordDayViewDTO of(
            LocalDate referenceDate,
            Map<LocalTime, List<ViewItemTypeRecordDTO>> viewItemDTOListMapForTypeRecord,
            Map<LocalTime, List<ViewItemTypeScheduledDTO>> viewItemDTOListMapForTypeScheduled
    ) {
        return new MedIntakeRecordDayViewDTO(referenceDate, viewItemDTOListMapForTypeRecord, viewItemDTOListMapForTypeScheduled);
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ViewItemTypeRecordDTO implements Serializable {

        private final RecordViewType recordViewType = RecordViewType.RECORD;

        // 약 정보 전달 목적
        private final MedDTO medDTO;
        // 복용 기록 정보 전달 목적
        private final MedIntakeRecordDTO medIntakeRecordDTO;

        // 기준 시점 그룹화 목적
        private final LocalDateTime referenceDateTime;

        @JsonCreator
        public ViewItemTypeRecordDTO(
                @JsonProperty("medDTO")
                MedDTO medDTO,
                @JsonProperty("medIntakeRecordDTO")
                MedIntakeRecordDTO medIntakeRecordDTO,

                @JsonProperty("referenceDateTime")
                @JsonSerialize(using = LocalDateTimeSerializer.class)
                @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                LocalDateTime referenceDateTime
        ) {
            this.medDTO = medDTO;
            this.medIntakeRecordDTO = medIntakeRecordDTO;
            this.referenceDateTime = referenceDateTime;
        }


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
    @Builder(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ViewItemTypeScheduledDTO implements Serializable {
        private final RecordViewType recordViewType = RecordViewType.SCHEDULED;

        private final MedUuid medUuid;
        private final String medName;
        private final MedType medType;
        private final Float doseAmount;
        private final DoseUnit doseUnit;
        private final Integer doseCount;
        private final LocalTime estimatedDoseTime;

        private final LocalDateTime referenceDateTime;

        @JsonCreator
        public ViewItemTypeScheduledDTO(
                @JsonProperty("medUuid")
                MedUuid medUuid,
                @JsonProperty("medName")
                String medName,
                @JsonProperty("medType")
                MedType medType,
                @JsonProperty("doseAmount")
                Float doseAmount,
                @JsonProperty("doseUnit")
                DoseUnit doseUnit,
                @JsonProperty("doseCount")
                Integer doseCount,

                @JsonProperty("estimatedDoseTime")
                @JsonSerialize(using = LocalTimeSerializer.class)
                @JsonDeserialize(using = LocalTimeDeserializer.class)
                LocalTime estimatedDoseTime,

                @JsonProperty("referenceDateTime")
                @JsonSerialize(using = LocalDateTimeSerializer.class)
                @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                LocalDateTime referenceDateTime
        ) {
            this.medUuid = medUuid;
            this.medName = medName;
            this.medType = medType;
            this.doseAmount = doseAmount;
            this.doseUnit = doseUnit;
            this.doseCount = doseCount;
            this.estimatedDoseTime = estimatedDoseTime;
            this.referenceDateTime = referenceDateTime;
        }


        public static ViewItemTypeScheduledDTO of(MedViewProjection projection, Integer doseCount, LocalDateTime referenceDateTime) {
            if (projection == null) {
                throw new IllegalArgumentException("Med projection cannot be null");
            }
            if (referenceDateTime == null) {
                throw new IllegalArgumentException("ReferenceDateTime cannot be null");
            }

            return ViewItemTypeScheduledDTO.builder()
                    .medUuid(projection.getMedUuid())
                    .medName(projection.getMedName())
                    .medType(projection.getMedType())
                    .doseAmount(projection.getDoseAmount())
                    .doseUnit(projection.getDoseUnit())
                    .doseCount(doseCount)
                    .estimatedDoseTime(referenceDateTime.toLocalTime())
                    .referenceDateTime(referenceDateTime)
                    .build();
        }
    }
}
