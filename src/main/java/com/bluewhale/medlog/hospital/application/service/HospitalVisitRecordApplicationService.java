package com.bluewhale.medlog.hospital.application.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.GetHospitalVisitRecordDTOByVisitUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.GetHospitalVisitRecordDTOListByAppUserUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.GetHospitalVisitRecordDTOWrapperByAppUserUuidUseCase;
import com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord.RegisterNewHospitalVisitRecordUseCase;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import com.bluewhale.medlog.hospital.dto.wrapper.HospitalVisitRecordDTOWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordApplicationService {

    private final RegisterNewHospitalVisitRecordUseCase registerNewHospitalVisitRecordUseCase;
    private final GetHospitalVisitRecordDTOListByAppUserUuidUseCase getHospitalVisitRecordDTOListByAppUserUuidUseCase;
    private final GetHospitalVisitRecordDTOByVisitUuidUseCase getHospitalVisitRecordDTOByVisitUuidUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        registerNewHospitalVisitRecordUseCase.execute(dto);
    }

    @Transactional(readOnly = true)
    public List<HospitalVisitRecordDTO> getHospitalVisitRecordDTOListByAppUserUuid(AppUserUuid appUserUuid) {
        return getHospitalVisitRecordDTOListByAppUserUuidUseCase.execute(appUserUuid);
    }

    private final GetHospitalVisitRecordDTOWrapperByAppUserUuidUseCase getHospitalVisitRecordDTOWrapperByAppUserUuidUseCase;

    @Transactional(readOnly = true)
    @Cacheable(key = "#appUserUuid", value = "appUserHospitalVisitRecordDTO")
    public HospitalVisitRecordDTOWrapper getHospitalVisitRecordWrapperByAppUserUuid(AppUserUuid appUserUuid) {
        return getHospitalVisitRecordDTOWrapperByAppUserUuidUseCase.execute(appUserUuid);
    }

    @Transactional(readOnly = true)
    public HospitalVisitRecordDTO getHospitalVisitRecordDTOByVisitUuid(VisitUuid visitUuid) {
        return getHospitalVisitRecordDTOByVisitUuidUseCase.execute(visitUuid);
    }

}
