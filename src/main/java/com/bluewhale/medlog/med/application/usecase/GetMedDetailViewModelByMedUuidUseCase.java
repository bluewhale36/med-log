package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedDetailViewModel;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetMedDetailViewModelByMedUuidUseCase implements UseCase<MedUuid, MedDetailViewModel> {

    private final MedIdentifierConvertService medIdConvertService;
    private final MedRepository medRepository;

    @Override
    public MedDetailViewModel execute(MedUuid input) {
        Long medId = medIdConvertService.getIdByUuid(input);
        Med med = medRepository.findByIdWithHospitalVisitRecordAndAppUser(medId);
        return MedDetailViewModel.from(med);
    }
}
