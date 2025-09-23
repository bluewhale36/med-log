package com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.wrapper.HospitalVisitRecordDTOWrapper;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetHospitalVisitRecordDTOWrapperByAppUserUuidUseCase
        implements UseCase<AppUserUuid, HospitalVisitRecordDTOWrapper> {

    private final AppUserIdentifierConvertService appUserIdConvertService;

    private final HospitalVisitRecordRepository repository;


    @Override
    public HospitalVisitRecordDTOWrapper execute(AppUserUuid input) {
        Long appUserId = appUserIdConvertService.getIdByUuid(input);
        List<HospitalVisitRecord> entityList = repository.findAllByAppUserId(appUserId);
        List<HospitalVisitRecordDTO> dtoList = entityList.stream().map(HospitalVisitRecordDTO::from).toList();
        return new HospitalVisitRecordDTOWrapper(dtoList);
    }
}
