package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.dto.MedDTO;
import com.bluewhale.medlog.med.dto.wrapper.MedDTOWrapper;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetMedDTOWrapperByAppUserUuid implements UseCase<AppUserUuid, MedDTOWrapper> {

    private final AppUserIdentifierConvertService appUserIdConvertService;
    private final MedRepository medRepository;

    @Override
    public MedDTOWrapper execute(AppUserUuid input) {
        Long appUserId = appUserIdConvertService.getIdByUuid(input);
        List<Med> entityList = medRepository.findAllByAppUserId(appUserId);
        List<MedDTO> medDTOList = entityList.stream().map(MedDTO::from).toList();
        return MedDTOWrapper.of(medDTOList);
    }
}
