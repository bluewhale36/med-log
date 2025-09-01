package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.common.service.IF_ConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordConvertService_Impl implements IF_ConvertService<MedIntakeRecord, MedIntakeRecordUuid> {

    private final MedIntakeRecordRepository mirRepository;

    @Override
    public Long getIdByUuid(MedIntakeRecordUuid uuid) {
        return getEntityByUuid(uuid).getMedIntakeRecordId();
    }

    @Override
    public MedIntakeRecord getEntityByUuid(MedIntakeRecordUuid uuid) {
        return mirRepository.findByMedIntakeRecordUuid(uuid).orElseThrow(
                () -> new IllegalArgumentException(String.format("MedIntakeRecord with uuid %s not found", uuid))
        );
    }

    @Override
    public MedIntakeRecordUuid getUuidById(Long id) {
        return mirRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("MedIntakeRecord with id %s not found", id))
        ).getMedIntakeRecordUuid();
    }
}
