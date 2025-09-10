package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterNewMedIntakeRecordDTOListUseCase implements UseCase<List<MedIntakeRecordRegisterDTO>, List<MedIntakeRecordDTO>> {
    @Override
    public List<MedIntakeRecordDTO> execute(List<MedIntakeRecordRegisterDTO> input) {
        return null;
    }
}
