package com.bluewhale.medlog.med.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.usecase.med.GetMedDTOListByAppUserUuidUseCase;
import com.bluewhale.medlog.med.application.usecase.med.ModifyMedUseCase;
import com.bluewhale.medlog.med.application.usecase.med.SoftDeleteMedUseCase;
import com.bluewhale.medlog.med.application.usecase.med.RegisterNewMedUseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.medintakesnapshot.application.usecase.CreateNewMedSnapshotByMedUuidUseCase;
import com.bluewhale.medlog.medintakesnapshot.application.usecase.ModifyMedSnapshotByMedUuidUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Med Domain 내에서 발생하는 모든 비즈니스 흐름을 UseCase 단위로 구분하여
 * 그 흐름을 제어하는 Service Layer.
 * 세부적인 Service 및 Repository Layer 의 호출은 각 UseCase 내에서 필요에 따라 DI 하여 진행됨.
 */
@Service
@RequiredArgsConstructor
public class MedApplicationService {

    private final RegisterNewMedUseCase regiNewMedUseCase;
    private final CreateNewMedSnapshotByMedUuidUseCase createNewMedSnapshotByMedUuidUseCase;

    /**
     * 새로운 약 정보를 등록하고, 해당 약에 대한 스냅샷을 생성합니다.
     *
     * @param payload 약 등록에 필요한 데이터 맵
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerNewMed(Map<String, Object> payload) {
        MedDTO insertedMedDTO = regiNewMedUseCase.execute(payload);
        createNewMedSnapshotByMedUuidUseCase.execute(insertedMedDTO.getMedUuid());
    }


    private final SoftDeleteMedUseCase softDeleteMedUseCase;

    /**
     * 약의 UUID를 기반으로 해당 약 정보를 소프트 삭제 처리합니다.
     *
     * @param medUuid 소프트 삭제할 약의 UUID
     */
    @Transactional(rollbackFor = Exception.class)
    public void softDeleteMedWithMedUuid(MedUuid medUuid) {
        softDeleteMedUseCase.execute(medUuid);
    }


    private final GetMedDTOListByAppUserUuidUseCase getMedDTOListByAppUserUuidUseCase;

    /**
     * 사용자 UUID를 기반으로 해당 사용자의 약 정보 리스트를 조회합니다.
     *
     * @param appUserUuid 약 정보를 조회할 사용자 UUID
     * @return 해당 사용자의 약 정보 리스트
     */
    @Transactional(readOnly = true)
    public List<MedDTO> getMedDTOListByAppUserUuid(AppUserUuid appUserUuid) {
        return getMedDTOListByAppUserUuidUseCase.execute(appUserUuid);
    }


    private final ModifyMedUseCase modifyMedUseCase;
    private final ModifyMedSnapshotByMedUuidUseCase modifyMedSnapshotByMedUuidUseCase;

    /**
     * 약 정보 및 해당 약의 스냅샷 정보를 수정합니다.
     *
     * @param payload 약 정보 수정에 필요한 데이터 맵
     * @return 수정된 약 정보 DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public MedDTO updateMedInfo(Map<String, Object> payload) {
        MedDTO modifiedMedDTO = modifyMedUseCase.execute(payload);
        modifyMedSnapshotByMedUuidUseCase.execute(modifiedMedDTO.getMedUuid());
        return modifiedMedDTO;
    }

}
