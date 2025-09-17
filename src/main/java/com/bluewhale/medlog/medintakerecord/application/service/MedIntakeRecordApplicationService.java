package com.bluewhale.medlog.medintakerecord.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.medintakerecord.application.usecase.GetRecordViewDTOByReferenceDateUseCase;
import com.bluewhale.medlog.medintakerecord.application.usecase.RegisterNewMedIntakeRecordDTOListUseCase;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import com.bluewhale.medlog.medintakerecord.model.RenderServiceRequestToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordApplicationService {

    private final GetRecordViewDTOByReferenceDateUseCase getRecordViewDTOByReferenceDateUseCase;

    private final RegisterNewMedIntakeRecordDTOListUseCase registerNewMedIntakeRecordDTOListUseCase;

    @Transactional(readOnly = true)
    public Optional<MedIntakeRecordDayViewDTO> getDTOListForIntakeRecordView(
            AppUserUuid appUserUuid, LocalDate referenceDate
    ) {
        return getRecordViewDTOByReferenceDateUseCase.execute(
                RenderServiceRequestToken.of(appUserUuid, referenceDate)
        );
    }

    @Transactional
    public void registerNewMedIntakeRecordList(List<MedIntakeRecordRegisterDTO> medIntakeRecordRegisterDTOList) {
        List<MedIntakeRecordDTO> medIntakeRecordDTOList =
                registerNewMedIntakeRecordDTOListUseCase.execute(medIntakeRecordRegisterDTOList);
    }
}
