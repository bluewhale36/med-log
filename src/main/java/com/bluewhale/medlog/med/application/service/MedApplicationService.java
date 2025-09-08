package com.bluewhale.medlog.med.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.usecase.med.GetMedDTOListByAppUserUuidUseCase;
import com.bluewhale.medlog.med.application.usecase.med.SoftDeleteMedUseCase;
import com.bluewhale.medlog.med.application.usecase.med.RegisterNewMedUseCase;
import com.bluewhale.medlog.med.application.usecase.medintakerecord.GetMedIntakeRecordViewDTOListUseCase;
import com.bluewhale.medlog.med.application.usecase.medintakesnapshot.CreateOrModifyNewMedSnapshotUseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
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
    private final CreateOrModifyNewMedSnapshotUseCase createOrModifyNewMedSnapshotUseCase;
    private final GetMedIntakeRecordViewDTOListUseCase getMedIntakeRecordViewDTOListUseCase;
    private final SoftDeleteMedUseCase softDeleteMedUseCase;
    private final GetMedDTOListByAppUserUuidUseCase getMedDTOListByAppUserUuidUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewMed(Map<String, Object> payload) {
        MedDTO insertedMedDTO = regiNewMedUseCase.execute(payload);
        createOrModifyNewMedSnapshotUseCase.execute(insertedMedDTO.getAppUserUuid());
    }

    public List<MedIntakeRecordDayViewDTO> getDTOListForIntakeRecord(AppUserUuid appUserUuid) {
        return getMedIntakeRecordViewDTOListUseCase.execute(appUserUuid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void softDeleteMedWithMedUuid(MedUuid medUuid) {
        softDeleteMedUseCase.execute(medUuid);
    }

    @Transactional(readOnly = true)
    public List<MedDTO> getMedDTOListByAppUserUuid(AppUserUuid appUserUuid) {
        return getMedDTOListByAppUserUuidUseCase.execute(appUserUuid);
    }
}
