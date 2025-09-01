package com.bluewhale.medlog.medintakesnapshot.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.service.MedConvertService_Impl;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotAggregationDTO;
import com.bluewhale.medlog.medintakesnapshot.mapper.MedIntakeSnapshotMapper;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeSnapshotAggregationService {

    private final MedIntakeSnapshotMapper misMapper;

    private final SnapshotPolicyRepository spRepo;

    private final MedConvertService_Impl medCServ;
    private final AppUserConvertService_Impl appUserCServ;


    public List<MedIntakeSnapshotAggregationDTO> getMisAggDTOListByMedId(Long medId) {
        List<MedIntakeSnapshotAggregationDTO> resultList = new ArrayList<>();

        List<MedIntakeSnapshot> entityList = spRepo.findAllByMedId(medId);
        for (MedIntakeSnapshot entity : entityList) {
            MedUuid medUuid = medCServ.getUuidById(entity.getMedId());
            AppUserUuid appUserUuid = appUserCServ.getUuidById(entity.getAppUserId());

            resultList.add(misMapper.toAggregationDTO(entity, appUserUuid, medUuid));
        }
        return resultList;
    }

    public List<MedIntakeSnapshotAggregationDTO> getMisAggDTOListByMedIdList(List<Long> medIdList) {
        List<MedIntakeSnapshotAggregationDTO> resultList = new ArrayList<>();
        for (Long medId : medIdList) {
            resultList.addAll(getMisAggDTOListByMedId(medId));
        }
        return resultList;
    }
}
