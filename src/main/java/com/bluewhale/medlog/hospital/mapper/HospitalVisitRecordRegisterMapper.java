package com.bluewhale.medlog.hospital.mapper;

import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import org.springframework.stereotype.Component;

@Component
public class HospitalVisitRecordRegisterMapper {

    public HospitalVisitRecord toEntity(HospitalVisitRecordRegisterDTO dto, VisitUuid visitUuid, Long appUserId) {
        return HospitalVisitRecord.builder()
                .visitId(null)
                .visitUuid(visitUuid)
                .appUserId(appUserId)
                .hospitalName(dto.getHospitalName())
                .consultedAt(dto.getConsultedAt())
                .chiefSymptom(dto.getChiefSymptom())
                .diagnosis(dto.getDiagnosis())
                .physicianName(dto.getPhysicianName())
                .build();
    }
}
