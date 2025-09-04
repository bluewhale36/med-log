package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordConvertService_Impl;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.med.mapper.MedMapper;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.service.MedIntakeRecordAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedAggregationService {

    private final MedRepository medRepo;

    private final MedMapper medMapper;

    private final HospitalVisitRecordConvertService_Impl hvrQServ;
    private final AppUserConvertService_Impl appUserQServ;

    private final MedIntakeRecordAggregationService mirFServ;

    public List<MedAggregationDTO> getMedAggDTOListByAppUserId(Long appUserId) {
        return medRepo.findAllByAppUserId(appUserId).stream()
                .map(Med::getMedId)
                .map(this::getMedAggDTOByMedId)
                .toList();
    }

    public MedAggregationDTO getMedAggDTOByMedId(Long medId) {
        Med entity = medRepo.findById(medId).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication for MedUuid %s not found", medId))
        );
        VisitUuid visitUuid = entity.getVisitId() != null ? hvrQServ.getUuidById(entity.getVisitId()) : null;
        AppUserUuid appUserUuid = appUserQServ.getUuidById(entity.getAppUserId());
        List<MedIntakeRecordAggregationDTO> mirFullDTOList = mirFServ.getMirFullDTOListByMedId(medId);

        return medMapper.toFullDTO(entity, visitUuid, appUserUuid, mirFullDTOList);
    }
}
