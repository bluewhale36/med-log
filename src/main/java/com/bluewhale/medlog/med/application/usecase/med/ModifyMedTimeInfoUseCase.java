package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedTimeModifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ModifyMedTimeInfoUseCase implements UseCase<MedTimeModifyDTO, MedDTO> {


    @Override
    public MedDTO execute(MedTimeModifyDTO input) {
        return null;
    }
}
