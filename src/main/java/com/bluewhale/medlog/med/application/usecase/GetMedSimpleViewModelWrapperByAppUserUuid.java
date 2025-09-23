package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.dto.MedSimpleViewModel;
import com.bluewhale.medlog.med.dto.wrapper.MedSimpleViewModelWrapper;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMedSimpleViewModelWrapperByAppUserUuid
        implements UseCase<AppUserUuid, MedSimpleViewModelWrapper> {

    private final AppUserIdentifierConvertService appUserIdentifierConvertService;
    private final MedRepository medRepository;

    @Override
    public MedSimpleViewModelWrapper execute(AppUserUuid input) {
        Long appUserId = appUserIdentifierConvertService.getIdByUuid(input);
        List<Med> entityList = medRepository.findAllByAppUserId(appUserId);
        List<MedSimpleViewModel> medSimpleViewModelList = entityList.stream().map(MedSimpleViewModel::from).toList();
        return new MedSimpleViewModelWrapper(medSimpleViewModelList);
    }
}
