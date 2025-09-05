package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.service.MedConvertService_Impl;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordService {

    private final MedIntakeRecordRepository mirRepo;

    private final MedConvertService_Impl medCServ;

    public List<MedIntakeRecordDTO> getMedIntakeRecordListByMedUuid(MedUuid medUuid) {
        return mirRepo.findByMedId(medCServ.getIdByUuid(medUuid)).stream().map(MedIntakeRecordDTO::from).toList();
    }



}
