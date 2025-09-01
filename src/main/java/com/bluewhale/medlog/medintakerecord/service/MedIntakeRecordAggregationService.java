package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.service.MedConvertService_Impl;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.mapper.MedIntakeRecordMapper;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordAggregationService {

    private final MedConvertService_Impl medQServ;

    private final MedIntakeRecordRepository mirRepo;

    private final MedIntakeRecordMapper mirMapper;

    public List<MedIntakeRecordAggregationDTO> getMirFullDTOListByMedId(Long medId) {
        MedUuid uuid = medQServ.getUuidById(medId);

        return mirRepo.findByMedId(medId).stream()
                .map(entity -> mirMapper.toFullDTO(entity, uuid)).toList();
    }
}
