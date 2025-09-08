package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordAggregationService {

    private final MedIdentifierConvertService medQServ;

    private final MedIntakeRecordRepository mirRepo;


    public List<MedIntakeRecordAggregationDTO> getMirFullDTOListByMedId(Long medId) {
        return mirRepo.findByMedId(medId).stream().map(this::toAggregationDTO).toList();
    }


    private MedIntakeRecordAggregationDTO toAggregationDTO(MedIntakeRecord entity) {
        return MedIntakeRecordAggregationDTO.builder()
                .medIntakeRecordId(entity.getMedIntakeRecordId())
                .medIntakeRecordUuid(entity.getMedIntakeRecordUuid())
                .medId(entity.getMed().getMedId())
                .medUuid(entity.getMed().getMedUuid())
                .isTaken(entity.isTaken())
                .estimatedDoseTime(entity.getEstimatedDoseTime())
                .takenAt(entity.getTakenAt())
                .build();
    }

}
