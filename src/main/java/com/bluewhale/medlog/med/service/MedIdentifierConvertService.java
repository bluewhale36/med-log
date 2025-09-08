package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.service.AbstractIdentifierConvertService;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MedIdentifierConvertService
        extends AbstractIdentifierConvertService<Med, MedUuid> {

    private final MedRepository medRepository;


    @Override
    protected IdentifierRoutableRepository<Med, Long, MedUuid> getRepository() {
        return medRepository;
    }

    @Override
    protected Function<Long, Optional<MedUuid>> uuidFinder() {
        return medRepository::findUuidById;
    }

    @Override
    protected Function<MedUuid, Optional<Long>> idFinder() {
        return medRepository::findIdByUuid;
    }

    @Override
    protected String getEntityName() {
        return "Med";
    }
}
