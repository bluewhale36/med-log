package com.bluewhale.medlog.medintakerecord.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.usecase.medintakesnapshot.ModifyIsTakenInMedSnapshotUseCase;
import com.bluewhale.medlog.medintakerecord.application.usecase.GetMedIntakeRecordViewDTOListUseCase;
import com.bluewhale.medlog.medintakerecord.application.usecase.RegisterNewMedIntakeRecordDTOListUseCase;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotModifyIsTakenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordApplicationService {

    private final GetMedIntakeRecordViewDTOListUseCase getMedIntakeRecordViewDTOListUseCase;

    private final RegisterNewMedIntakeRecordDTOListUseCase registerNewMedIntakeRecordDTOListUseCase;
    private final ModifyIsTakenInMedSnapshotUseCase modifyIsTakenInMedSnapshotUseCase;

    @Transactional(readOnly = true)
    public List<MedIntakeRecordDayViewDTO> getDTOListForIntakeRecordView(AppUserUuid appUserUuid) {
        return getMedIntakeRecordViewDTOListUseCase.execute(appUserUuid);
    }

    @Transactional
    public void registerNewMedIntakeRecordList(List<MedIntakeRecordRegisterDTO> medIntakeRecordRegisterDTOList) {
        List<MedIntakeRecordDTO> medIntakeRecordDTOList =
                registerNewMedIntakeRecordDTOListUseCase.execute(medIntakeRecordRegisterDTOList);

        modifyIsTakenInMedSnapshotUseCase.execute(
                medIntakeRecordDTOList.stream().map(MedIntakeSnapshotModifyIsTakenDTO::from).toList()
        );
    }
}
