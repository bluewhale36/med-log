package com.bluewhale.medlog.hospital.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.service.AbstractIdentifierConvertService;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordIdentifierConvertService
        extends AbstractIdentifierConvertService<HospitalVisitRecord, VisitUuid> {

    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;


    @Override
    protected IdentifierRoutableRepository<HospitalVisitRecord, Long, VisitUuid> getRepository() {
        return hospitalVisitRecordRepository;
    }

    @Override
    protected Function<Long, Optional<VisitUuid>> uuidFinder() {
        return hospitalVisitRecordRepository::findUuidById;
    }

    @Override
    protected Function<VisitUuid, Optional<Long>> idFinder() {
        return hospitalVisitRecordRepository::findIdByUuid;
    }

    @Override
    protected String getEntityName() {
        return "HospitalVisitRecord";
    }
}
