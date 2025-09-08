package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMedDTOListByAppUserUuidUseCase implements IF_UseCase<AppUserUuid, List<MedDTO>> {

    private final AppUserConvertService_Impl appUserConvertService;
    private final MedRepository medRepository;

    @Override
    public List<MedDTO> execute(AppUserUuid input) {
        Long appUserId = appUserConvertService.getIdByUuid(input);
        List<Med> entityList = medRepository.findAllByAppUserId(appUserId);
        return entityList.stream().map(MedDTO::from).toList();
    }
}
