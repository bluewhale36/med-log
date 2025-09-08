package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedIdentifierConvertService_Impl implements IdentifierConvertService<MedUuid> {

    private final MedRepository medRepository;


    @Override
    public Long getIdByUuid(MedUuid uuid) {
        if (uuid == null) {
            throw new NullIdentifierException("Uuid Value for Med is Null.");
        }

        return medRepository.findIdByMedUuid(uuid).orElseThrow(
                () -> new IllegalIdentifierException("Med with Uuid " + uuid + " has not been found.")
        );
    }

    @Override
    public MedUuid getUuidById(Long id) {
        if (id == null) {
            throw new NullIdentifierException("Id Value for Med is Null.");
        }

        Med entityReference = medRepository.getReferenceById(id);

        MedUuid medUuid;
        try {
            medUuid = entityReference.getMedUuid();
        } catch (EntityNotFoundException e) {
            throw new IllegalIdentifierException("Med with id " + id + " has not been found.");
        }
        return medUuid;
    }
}
