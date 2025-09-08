package com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterNewHospitalVisitRecordUseCase implements UseCase<HospitalVisitRecordRegisterDTO, Void> {

    private final AppUserConvertService_Impl appUserConvertService;
    private final AppUserRepository appUserRepository;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;

    @Override
    public Void execute(HospitalVisitRecordRegisterDTO input) {
        Long appUserId = appUserConvertService.getIdByUuid(input.getAppUserUuid());
        AppUser appUserReference = appUserRepository.getReferenceById(appUserId);

        HospitalVisitRecord savingEntity = HospitalVisitRecord.create(input, appUserReference);
        hospitalVisitRecordRepository.save(savingEntity);

        return null;
    }
}
