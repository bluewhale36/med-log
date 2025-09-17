package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordIdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegisterNewMedUseCase implements UseCase<Map<String, Object>, MedDTO> {

    private final MedService medService;
    private final MedRepository medRepository;

    private final AppUserRepository appUserRepository;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;

    private final AppUserIdentifierConvertService appUserIdentifierConvertService;
    private final HospitalVisitRecordIdentifierConvertService hospitalVisitRecordIdentifierConvertService;

    @Override
    public MedDTO execute(Map<String, Object> input) {
        MedRegisterDTO regiDTO = medService.getMedRegisterDTOFromPayload(input);

        Long appUserId = appUserIdentifierConvertService.getIdByUuid(regiDTO.getAppUserUuid());
        Long visitId =
                regiDTO.getVisitUuid() != null ?
                hospitalVisitRecordIdentifierConvertService.getIdByUuid(regiDTO.getVisitUuid()) :
                null;

        AppUser appUserReference = appUserRepository.getReferenceById(appUserId);
        HospitalVisitRecord hospitalVisitRecordReference = visitId != null ? hospitalVisitRecordRepository.getReferenceById(visitId) : null;

        Med entity = Med.create(regiDTO, appUserReference, hospitalVisitRecordReference);
        Med insertedEntity = medRepository.save(entity);

        return MedDTO.from(insertedEntity);
    }
}
