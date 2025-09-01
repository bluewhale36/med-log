package com.bluewhale.medlog.hospital.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import com.bluewhale.medlog.hospital.mapper.HospitalVisitRecordMapper;
import com.bluewhale.medlog.hospital.mapper.HospitalVisitRecordRegisterMapper;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalVisitRecordService {

    private final HospitalVisitRecordRepository repository;

    private final AppUserConvertService_Impl appUserQs;

    private final HospitalVisitRecordRegisterMapper hvrRegisterMapper;
    private final HospitalVisitRecordMapper hvrMapper;
    private final HospitalVisitRecordConvertService_Impl hvrQs;

    public void registerNewHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        HospitalVisitRecord hvrEntity = convertHospitalVisitRecord(dto), insertedHospitalVisitRecord;
        insertedHospitalVisitRecord = repository.save(hvrEntity);

        System.out.println(insertedHospitalVisitRecord);
    }

    public List<HospitalVisitRecordDTO> getHospitalVisitRecordListByAppUserUuid(AppUserUuid appUserUuid) {
        List<HospitalVisitRecord> entityList = repository.findAllByAppUserId(getAppUserIdFromAppUserUuid(appUserUuid));

        return entityList.stream()
                .map(e -> hvrMapper.toDTO(e, appUserUuid))
                .sorted((d1, d2) -> -d1.getConsultedAt().compareTo(d2.getConsultedAt()))
                .toList();
    }

    public HospitalVisitRecordDTO getOneHospitalVisitRecordByVisitUuid(VisitUuid visitUuid) {
        HospitalVisitRecord entity = repository.findById(getVisitIdFromVisitUuid(visitUuid)).orElseThrow(
                () -> new IllegalArgumentException(String.format("Visit Log for VisitUuid %s not found", visitUuid))
        );
        return hvrMapper.toDTO(entity, getAppUserUuidFromAppUserId(entity.getAppUserId()));
    }

    private HospitalVisitRecord convertHospitalVisitRecord(HospitalVisitRecordRegisterDTO dto) {
        VisitUuid uuid = new VisitUuid(UUID.randomUUID().toString());
        Long appUserId = getAppUserIdFromAppUserUuid(dto.getAppUserUuid());

        return hvrRegisterMapper.toEntity(dto, uuid, appUserId);
    }

    private Long getAppUserIdFromAppUserUuid(AppUserUuid appUserUuid) {
        return appUserUuid != null ? appUserQs.getIdByUuid(appUserUuid) : null;
    }

    private Long getVisitIdFromVisitUuid(VisitUuid visitUuid) {
        return visitUuid != null ? hvrQs.getIdByUuid(visitUuid) : null;
    }

    private AppUserUuid getAppUserUuidFromAppUserId(Long appUserId) {
        return appUserId != null ? appUserQs.getUuidById(appUserId) : null;
    }
}
