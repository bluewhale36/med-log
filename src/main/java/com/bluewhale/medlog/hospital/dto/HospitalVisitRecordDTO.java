package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class HospitalVisitRecordDTO {

    private final VisitUuid visitUuid;
    private final AppUserUuid appUserUuid;
    private final String hospitalName;
    private final LocalDateTime consultedAt;
    private final String chiefSymptom;
    private final String diagnosis;
    private final String physicianName;

    @JsonCreator
    public HospitalVisitRecordDTO(
            @JsonProperty("visitUuid") VisitUuid visitUuid,
            @JsonProperty("appUserUuid") AppUserUuid appUserUuid,
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
        this.appUserUuid = appUserUuid;
        this.hospitalName = hospitalName;
        this.consultedAt = consultedAt;
        this.chiefSymptom = chiefSymptom;
        this.diagnosis = diagnosis;
        this.physicianName = physicianName;
    }

    public static HospitalVisitRecordDTO from(HospitalVisitRecord entity) {
        return HospitalVisitRecordDTO.builder()
                .visitUuid(entity.getVisitUuid())
                .appUserUuid(entity.getAppUser().getAppUserUuid())
                .hospitalName(entity.getHospitalName())
                .consultedAt(entity.getConsultedAt())
                .chiefSymptom(entity.getChiefSymptom())
                .diagnosis(entity.getDiagnosis())
                .physicianName(entity.getPhysicianName())
                .build();
    }
}
