package com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordConvertService_Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetHospitalVisitRecordDTOByVisitUuidUseCase implements UseCase<VisitUuid, HospitalVisitRecordDTO> {

    private final HospitalVisitRecordConvertService_Impl hospitalVisitRecordConvertService;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;

    @Override
    public HospitalVisitRecordDTO execute(VisitUuid input) {
        Long visitId = hospitalVisitRecordConvertService.getIdByUuid(input);
        if (visitId == null) {
            throw new IllegalArgumentException("Invalid VisitUuid: " + input);
        }
        HospitalVisitRecord entity = hospitalVisitRecordRepository.findById(visitId).orElseThrow(
                () -> new IllegalArgumentException("No HospitalVisitRecord found with id: " + visitId)
        );
        return HospitalVisitRecordDTO.from(entity);
    }
}
