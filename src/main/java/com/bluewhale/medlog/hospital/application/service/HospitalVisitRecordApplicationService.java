package com.bluewhale.medlog.hospital.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.application.usecase.GetHospitalVisitRecordListByAppUserUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.RegisterNewHospitalVisitRecordUseCase;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordApplicationService {

    private final RegisterNewHospitalVisitRecordUseCase registerNewHospitalVisitRecordUseCase;
    private final GetHospitalVisitRecordListByAppUserUuidUseCase getHospitalVisitRecordListByAppUserUuidUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        registerNewHospitalVisitRecordUseCase.execute(dto);
    }

    @Transactional(readOnly = true)
    public List<HospitalVisitRecordDTO> getHospitalVisitRecordListByAppUserUuid(AppUserUuid appUserUuid) {
        return getHospitalVisitRecordListByAppUserUuidUseCase.execute(appUserUuid);
    }

}
