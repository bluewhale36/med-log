package com.bluewhale.medlog.medintakerecord.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.medintakerecord.application.usecase.GetMedIntakeRecordViewDTOListUseCase;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordApplicationService {

    private final GetMedIntakeRecordViewDTOListUseCase getMedIntakeRecordViewDTOListUseCase;

    public List<MedIntakeRecordDayViewDTO> getDTOListForIntakeRecordView(AppUserUuid appUserUuid) {
        return getMedIntakeRecordViewDTOListUseCase.execute(appUserUuid);
    }
}
