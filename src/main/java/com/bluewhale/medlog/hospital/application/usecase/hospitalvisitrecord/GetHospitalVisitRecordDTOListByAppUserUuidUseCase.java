package com.bluewhale.medlog.hospital.application.usecase.hospitalvisitrecord;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetHospitalVisitRecordDTOListByAppUserUuidUseCase implements UseCase<AppUserUuid, List<HospitalVisitRecordDTO>> {

    private final AppUserIdentifierConvertService appUserConvertService;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;

    @Override
    public List<HospitalVisitRecordDTO> execute(AppUserUuid input) {
        Long appUserId = appUserConvertService.getIdByUuid(input);
        List<HospitalVisitRecord> entityList = hospitalVisitRecordRepository.findAllByAppUserId(appUserId);
        return entityList.stream().map(HospitalVisitRecordDTO::from).toList();
    }
}
