package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordIdentifierConvertService implements IdentifierConvertService<MedIntakeRecordUuid> {

    private final MedIntakeRecordRepository medIntakeRecordRepository;


    @Override
    public Long getIdByUuid(MedIntakeRecordUuid uuid) {
        if (uuid == null) {
            throw new NullIdentifierException("Uuid Value for MedIntakeRecord is Null.");
        }

        return medIntakeRecordRepository.findIdByMedIntakeRecordUuid(uuid).orElseThrow(
                () -> new IllegalIdentifierException("MedIntakeRecord with Uuid " + uuid + " has not been found.")
        );
    }

    @Override
    public MedIntakeRecordUuid getUuidById(Long id) {
        if (id == null) {
            throw new NullIdentifierException("Id Value for MedIntakeRecord is Null.");
        }

        MedIntakeRecord entityReference = medIntakeRecordRepository.getReferenceById(id);

        MedIntakeRecordUuid medIntakeRecordUuid;
        try {
            medIntakeRecordUuid = entityReference.getMedIntakeRecordUuid();
        }  catch (EntityNotFoundException e) {
            throw new IllegalIdentifierException("MedIntakeRecord with id " + id + " has not been found.");
        }
        return medIntakeRecordUuid;
    }
}
