package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.service.AbstractIdentifierConvertService;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordIdentifierConvertService
        extends AbstractIdentifierConvertService<MedIntakeRecord, MedIntakeRecordUuid> {

    private final MedIntakeRecordRepository medIntakeRecordRepository;


    @Override
    protected IdentifierRoutableRepository<MedIntakeRecord, Long, MedIntakeRecordUuid> getRepository() {
        return medIntakeRecordRepository;
    }

    @Override
    protected Function<Long, Optional<MedIntakeRecordUuid>> uuidFinder() {
        return medIntakeRecordRepository::findUuidById;
    }

    @Override
    protected Function<MedIntakeRecordUuid, Optional<Long>> idFinder() {
        return medIntakeRecordRepository::findIdByUuid;
    }

    @Override
    protected String getEntityName() {
        return "MedIntakeRecord";
    }
}
