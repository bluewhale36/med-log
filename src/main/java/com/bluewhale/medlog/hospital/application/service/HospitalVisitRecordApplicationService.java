package com.bluewhale.medlog.hospital.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.GetHospitalVisitRecordByVisitUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.GetHospitalVisitRecordListByAppUserUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.RegisterNewHospitalVisitRecordUseCase;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
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
    private final GetHospitalVisitRecordByVisitUuidUseCase getHospitalVisitRecordByVisitUuidUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        registerNewHospitalVisitRecordUseCase.execute(dto);
    }

    @Transactional(readOnly = true)
    public List<HospitalVisitRecordDTO> getHospitalVisitRecordListByAppUserUuid(AppUserUuid appUserUuid) {
        return getHospitalVisitRecordListByAppUserUuidUseCase.execute(appUserUuid);
    }

    @Transactional(readOnly = true)
    public HospitalVisitRecordDTO getHospitalVisitRecordByVisitUuid(VisitUuid visitUuid) {
        return getHospitalVisitRecordByVisitUuidUseCase.execute(visitUuid);
    }

}
