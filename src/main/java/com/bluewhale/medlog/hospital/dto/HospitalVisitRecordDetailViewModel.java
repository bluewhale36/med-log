package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record HospitalVisitRecordDetailViewModel(
        VisitUuid visitUuid,
        String hospitalName,
        LocalDateTime consultedAt,
        String chiefSymptom,
        String diagnosis,
        String physicianName
) {

    public static HospitalVisitRecordDetailViewModel from(HospitalVisitRecord entity) {
        return HospitalVisitRecordDetailViewModel.builder()
                .visitUuid(entity.getVisitUuid())
                .hospitalName(entity.getHospitalName())
                .consultedAt(entity.getConsultedAt())
                .diagnosis(entity.getDiagnosis())
                .physicianName(entity.getPhysicianName())
                .build();
    }
}
