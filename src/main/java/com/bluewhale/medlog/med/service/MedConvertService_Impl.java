package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.common.service.ConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedConvertService_Impl implements ConvertService<Med, MedUuid> {

    private final MedRepository medRepository;

    @Override
    public Long getIdByUuid(MedUuid medUuid) {
        return getEntityByUuid(medUuid).getMedId();
    }

    @Override
    public Med getEntityByUuid(MedUuid medUuid) {
        return medRepository.findByMedUuid(medUuid).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication with uuid %s not found", medUuid))
        );
    }

    @Override
    public MedUuid getUuidById(Long id) {
        Med med = medRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication with uuid %s not found", id))
        );
        return med.getMedUuid();
    }
}
