package com.bluewhale.medlog.hospital.dto;

import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
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
