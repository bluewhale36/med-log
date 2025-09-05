package com.bluewhale.medlog.med.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.usecase.med.DeleteMedUseCase;
import com.bluewhale.medlog.med.application.usecase.med.ModifyMedTimeInfoUseCase_Impl;
import com.bluewhale.medlog.med.application.usecase.med.RegisterNewMedUseCase_Impl;
import com.bluewhale.medlog.med.application.usecase.medintakerecord.GetMedIntakeRecordViewDTOListUseCase_Impl;
import com.bluewhale.medlog.med.application.usecase.medintakesnapshot.CreateOrModifyNewMedSnapshotUseCase_Impl;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedTimeModifyDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final RegisterNewMedUseCase_Impl regiNewMedUseCase;
    private final CreateOrModifyNewMedSnapshotUseCase_Impl createOrModifyNewMedSnapshotUseCase;
    private final GetMedIntakeRecordViewDTOListUseCase_Impl getMedIntakeRecordViewDTOListUseCase;
    private final ModifyMedTimeInfoUseCase_Impl modifyMedTimeInfoUseCase;
    private final DeleteMedUseCase deleteMedUseCase;

    @Transactional(rollbackOn = Exception.class)
    public void registerNewMed(Map<String, Object> payload) {
        MedDTO insertedMedDTO = regiNewMedUseCase.execute(payload);
        createOrModifyNewMedSnapshotUseCase.execute(insertedMedDTO.getAppUserUuid());
    }

    public List<MedIntakeRecordDayViewDTO> getDTOListForIntakeRecord(AppUserUuid appUserUuid) {
        return getMedIntakeRecordViewDTOListUseCase.execute(appUserUuid);
    }

    public void modifyMedTimeInformation(MedTimeModifyDTO modiDto) {
        MedDTO medDTO = modifyMedTimeInfoUseCase.execute(modiDto);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteMedWithRelatedInfo(MedUuid medUuid) {
        deleteMedUseCase.execute(medUuid);
    }
}
