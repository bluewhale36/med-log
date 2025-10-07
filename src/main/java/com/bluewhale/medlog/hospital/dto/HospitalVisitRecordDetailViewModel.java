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

@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class HospitalVisitRecordDetailViewModel {

    private final VisitUuid visitUuid;
    private final String hospitalName;
    private final LocalDateTime consultedAt;
    private final String chiefSymptom;
    private final String diagnosis;
    private final String physicianName;

    public HospitalVisitRecordDetailViewModel(
            @JsonProperty("visitUuid") VisitUuid visitUuid,
            @JsonProperty("hospitalName") String hospitalName,

            @JsonProperty("consultedAt")
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            LocalDateTime consultedAt,

            @JsonProperty("chiefSymptom") String chiefSymptom,
            @JsonProperty("diagnosis") String diagnosis,
            @JsonProperty("physicianName") String physicianName
    ) {
        this.visitUuid = visitUuid;
        this.hospitalName = hospitalName;
        this.consultedAt = consultedAt;
        this.chiefSymptom = chiefSymptom;
        this.diagnosis = diagnosis;
        this.physicianName = physicianName;
    }

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
