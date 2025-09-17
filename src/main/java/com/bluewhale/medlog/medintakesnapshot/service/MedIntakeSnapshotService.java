package com.bluewhale.medlog.medintakesnapshot.service;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.model.manager.PolicyManager;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.repository.MedIntakeSnapshotRepository;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedIntakeSnapshotService {

    private final MedRepository medRepository;
    private final MedIdentifierConvertService medIdentifierConvertService;

    private final PolicyManager policyManager;

    private final MedIntakeSnapshotRepository medIntakeSnapshotRepository;

    /**
     * 하나의 medDTO 에 대해서 snapshot 을 생성 또는 수정하는 로직.
     * @param medUuid : 하나의 medDTO 를 특정하는 UUID
     */
    public void registerMedIntakeSnapshotByMedDTO(MedUuid medUuid) {
        log.info("Registering MedIntakeSnapshot by medUuid: {}", medUuid);

        Long medId = medIdentifierConvertService.getIdByUuid(medUuid);
        log.info("Found Med Id: {} for Med UUID: {}", medId, medUuid);

        Med medEntity = medRepository.findById(medId).orElse(null);
        if (medEntity == null) {
            log.warn("No Med entity found for Id: {}", medId);
        } else {
            log.info("Found Med Entity: {}", medEntity);
        }

        PolicyRequestToken policyRequestToken = PolicyRequestToken.from(medEntity);
        log.info("New PolicyRequestToken created : {}", policyRequestToken);

        // 스냅샷 생성 정책 실행
        List<PolicyEvaluateResult> resultList = policyManager.evaluate(policyRequestToken);

        log.info("MedIntakeSnapshot is creating and saving...");
        medIntakeSnapshotRepository.saveAll(
                resultList.stream()
                        .map(r -> MedIntakeSnapshot.create(r, medEntity.getAppUser(), medEntity))
                        .toList()
        );
        log.info("MedIntakeSnapshot is creating and saving done.");
    }

    /**
     * 하나의 MedUuid 에 대해서 Snapshot 을 갱신하는 로직.
     * @param medUuid : 갱신 대상 약의 UUID
     */
    public void updateMedIntakeSnapshotByMedUuid(MedUuid medUuid) {
        deleteAllByMedUuid(medUuid);
        registerMedIntakeSnapshotByMedDTO(medUuid);
    }

    /**
     * 특정 약에 대한 모든 스냅샷 기록을 삭제.
     * @param medUuid : 삭제 대상 약의 UUID
     */
    public void deleteAllByMedUuid(MedUuid medUuid) {
        Long medId = medIdentifierConvertService.getIdByUuid(medUuid);
        medIntakeSnapshotRepository.deleteAllByMed_MedId(medId);
    }







}
