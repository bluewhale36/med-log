package com.bluewhale.medlog.medintakesnapshot.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedAggregationService;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService_Impl;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotDTO;
import com.bluewhale.medlog.medintakesnapshot.mapper.PolicyRequestTokenMapper;
import com.bluewhale.medlog.medintakesnapshot.model.manager.SnapshotPolicyManager;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeSnapshotService {

    private final SnapshotPolicyManager policyManager;
    private final SnapshotPolicyRepository policyRepo;

    private final PolicyRequestTokenMapper prtMapper;

    private final MedAggregationService medAggServ;

    private final AppUserIdentifierConvertService appUserCServ;
    private final MedIdentifierConvertService_Impl medCServ;

    private final AppUserRepository appUserRepository;
    private final MedRepository medRepository;


    public void generateMedIntakeSnapshotByAppUserUuidForAfter14Days(AppUserUuid appUserUuid) {
        List<PolicyEvaluateResult> resultList = new ArrayList<>();

        Long appUserId = appUserCServ.getIdByUuid(appUserUuid);
        List<MedAggregationDTO> medFDTOList = medAggServ.getMedAggDTOListByAppUserId(appUserId);

        for (int i = 0; i < 14; i++) {
            PolicyRequestToken policyReqToken = prtMapper.getPolicyReqToken(
                    appUserId, medFDTOList, LocalDate.now().plusDays(i)
            );
            resultList.addAll(policyManager.evaluate(List.of(policyReqToken)));
        }

        List<MedIntakeSnapshot> entityList = new ArrayList<>();
        AppUser appUser = appUserRepository.getReferenceById(appUserId);
        for (PolicyEvaluateResult result : resultList) {
            Med medReference = medRepository.getReferenceById(result.getMedId());
            entityList.add(MedIntakeSnapshot.create(result, appUser, medReference));
        }

        policyRepo.saveAll(entityList);
    }

    public List<MedIntakeSnapshotDTO> getMedIntakeSnapshotDTOListByMedUuid(MedUuid medUuid) {
        Long medId = medCServ.getIdByUuid(medUuid);
        List<MedIntakeSnapshot> entityList = policyRepo.findAllByMedId(medId);

        List<MedIntakeSnapshotDTO> dtoList = new ArrayList<>();
        for (MedIntakeSnapshot entity : entityList) {
            AppUserUuid appUserUuid = appUserCServ.getUuidById(entity.getAppUser().getAppUserId());
            dtoList.add(MedIntakeSnapshotDTO.from(entity));
        }
        return dtoList;
    }

    public List<MedIntakeSnapshotDTO> createOrModifyMedIntakeSnapshotByMedDTO(MedDTO medDTO) {

        /*
            하나의 med 정보에 대해서 snapshot 을 생성 또는 수정하는 로직.
            1. med 정보가 신규 정보일 경우
            2. med 정보가 기존 정보이나 일부 수정된 경우
         */

        /*
            medDTO 에 대한 medId 조회 및 유효한 snapshot 기록 있는 여부 판단
            1. 있을 경우 삭제 및 재생성.
            2. 없을 경우 생성.
         */

        return null;
    }

    public List<MedIntakeSnapshotDTO> updateMedIntakeSnapshotByMedDTO(MedDTO medDTO) {

        /*
            snapshot 정보 갱신.
            매개변수 등 필요 정보는 추후 수정 가능.
         */

        return null;
    }







}
