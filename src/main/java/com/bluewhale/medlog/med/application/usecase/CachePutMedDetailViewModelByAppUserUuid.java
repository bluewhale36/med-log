package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CachePutMedDetailViewModelByAppUserUuid
        implements UseCase<AppUserUuid, Void> {


    private final AppUserIdentifierConvertService appUserIdConvertService;
    private final MedRepository medRepository;
    private final MedService medService;

    @Override
    public Void execute(AppUserUuid input) {

        Long appUserId = appUserIdConvertService.getIdByUuid(input);

        List<Long> medIdList = medRepository.findAllByAppUserId(appUserId).stream()
                .map(Med::getMedId).toList();

        List<Med> fetchedMedList =
                medRepository.findAllByMedIdInWithHospitalVisitRecordAndAppUser(medIdList);

        for (Med med : fetchedMedList) {
            medService.cachePutMedDetailViewModel(med);
        }

        return null;
    }
}
