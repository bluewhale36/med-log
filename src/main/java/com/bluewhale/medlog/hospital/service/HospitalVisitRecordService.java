package com.bluewhale.medlog.hospital.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordService {

    private final HospitalVisitRecordRepository repository;

    private final AppUserConvertService_Impl appUserCServ;
    private final HospitalVisitRecordConvertService_Impl hvrCServ;

    private final AppUserRepository appUserRepository;

    public void registerNewHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        AppUser appUserReference = appUserRepository.getReferenceById(appUserCServ.getIdByUuid(dto.getAppUserUuid()));
        HospitalVisitRecord entity = HospitalVisitRecord.create(dto, appUserReference);

        System.out.println(repository.save(entity));
    }

    public List<HospitalVisitRecordDTO> getHospitalVisitRecordListByAppUserUuid(AppUserUuid appUserUuid) {
        List<HospitalVisitRecord> entityList = repository.findAllByAppUserId(appUserCServ.getIdByUuid(appUserUuid));
        return entityList.stream().map(HospitalVisitRecordDTO::from).toList();
    }

    public HospitalVisitRecordDTO getOneHospitalVisitRecordByVisitUuid(VisitUuid visitUuid) {
        HospitalVisitRecord entity = repository.findById(hvrCServ.getIdByUuid(visitUuid)).orElseThrow(
                () -> new IllegalArgumentException(String.format("Visit Log for VisitUuid %s not found", visitUuid))
        );
        return HospitalVisitRecordDTO.from(entity);
    }
}
