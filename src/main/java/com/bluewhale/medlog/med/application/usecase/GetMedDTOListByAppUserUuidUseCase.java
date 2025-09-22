package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMedDTOListByAppUserUuidUseCase implements UseCase<AppUserUuid, List<MedDTO>> {

    private final AppUserIdentifierConvertService appUserIdConvertService;
    private final MedRepository medRepository;

    @Override
    public List<MedDTO> execute(AppUserUuid input) {
        Long appUserId = appUserIdConvertService.getIdByUuid(input);
        return medRepository.findAllByAppUserId(appUserId).stream().map(MedDTO::from).toList();
    }
}
