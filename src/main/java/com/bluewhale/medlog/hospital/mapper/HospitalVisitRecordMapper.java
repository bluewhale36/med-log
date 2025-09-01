package com.bluewhale.medlog.hospital.mapper;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalVisitRecordMapper {

    public HospitalVisitRecordDTO toDTO(HospitalVisitRecord entity, AppUserUuid appUserUuid) {
        return HospitalVisitRecordDTO.builder()
                .visitUuid(entity.getVisitUuid())
                .appUserUuid(appUserUuid)
                .hospitalName(entity.getHospitalName())
                .consultedAt(entity.getConsultedAt())
                .chiefSymptom(entity.getChiefSymptom())
                .diagnosis(entity.getDiagnosis())
                .physicianName(entity.getPhysicianName())
                .build();
    }
}
