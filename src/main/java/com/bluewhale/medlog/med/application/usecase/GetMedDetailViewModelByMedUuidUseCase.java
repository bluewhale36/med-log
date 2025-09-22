package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDetailViewModel;
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
        log.info("MedDetailViewModel UseCase MedUuid: {}", input);
        Long medId = medIdConvertService.getIdByUuid(input);
        log.info("MedDetailViewModel UseCase MedId: {}", medId);
        Med med = medRepository.findByIdWithHospitalVisitRecordAndAppUser(medId);
        log.info("MedDetailViewModel UseCase med: {}", med);
        MedDetailViewModel result = MedDetailViewModel.from(med);
        log.info("MedDetailViewModel: {}", result);
        return result;
    }
}
