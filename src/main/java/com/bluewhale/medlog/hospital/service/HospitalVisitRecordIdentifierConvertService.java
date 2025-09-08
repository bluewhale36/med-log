package com.bluewhale.medlog.hospital.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordIdentifierConvertService implements IdentifierConvertService<VisitUuid> {

    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;


    @Override
    public Long getIdByUuid(VisitUuid uuid) {
        if (uuid == null) {
            throw new NullIdentifierException("Uuid Value for HospitalVisitRecord is Null.");
        }

        return hospitalVisitRecordRepository.findIdByVisitUuid(uuid).orElseThrow(
                () -> new IllegalIdentifierException("HospitalVisitRecord with Uuid " + uuid + " has not been found.")
        );
    }

    @Override
    public VisitUuid getUuidById(Long id) {
        if (id == null) {
            throw new NullIdentifierException("Id Value for HospitalVisitRecord is Null.");
        }

        HospitalVisitRecord entityReference = hospitalVisitRecordRepository.getReferenceById(id);

        VisitUuid visitUuid;
        try {
            visitUuid = entityReference.getVisitUuid();
        } catch (EntityNotFoundException e) {
            throw new IllegalIdentifierException("HospitalVisitRecord with id " + id + " has not been found.");
        }

        return visitUuid;
    }
}
