package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.service.MedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegisterNewMedUseCase_Impl implements IF_UseCase<Map<String, Object>, MedDTO> {

    private final MedService medServ;

    @Override
    public MedDTO execute(Map<String, Object> input) {
        MedRegisterDTO regiDTO = medServ.getMedRegiDTOFromPayload(input);
        Med entity = medServ.convertToNewMed(regiDTO),
                insertedMed = medServ.save(entity);
        return medServ.getMedDTOFromMed(insertedMed);
    }
}
