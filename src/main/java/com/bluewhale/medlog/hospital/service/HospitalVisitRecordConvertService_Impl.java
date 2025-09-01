package com.bluewhale.medlog.hospital.service;

import com.bluewhale.medlog.common.service.IF_ConvertService;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordConvertService_Impl implements IF_ConvertService<HospitalVisitRecord, VisitUuid> {

    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;

    @Override
    public Long getIdByUuid(VisitUuid uuid) {
        return getEntityByUuid(uuid).getVisitId();
    }

    @Override
    public HospitalVisitRecord getEntityByUuid(VisitUuid uuid) {
        return hospitalVisitRecordRepository.findByVisitUuid(uuid).orElseThrow(
                () -> new IllegalArgumentException(String.format("No hospital visit with visit uuid '%s' found", uuid))
        );
    }

    @Override
    public VisitUuid getUuidById(Long id) {
        HospitalVisitRecord hospitalVisitRecord = hospitalVisitRecordRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No hospital visit with id '%s' found", id))
        );
        return hospitalVisitRecord.getVisitUuid();
    }
}
