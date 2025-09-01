package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordConvertService_Impl;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.factory.MedRegisterDTOFactory;
import com.bluewhale.medlog.med.mapper.MedMapper;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedService {

    private final MedRegisterDTOFactory medRegiDTOFactory;
    private final MedMapper medMapper;

    private final HospitalVisitRecordConvertService_Impl hvCServ;
    private final AppUserConvertService_Impl appUserCServ;
    private final MedConvertService_Impl medCServ;

    private final MedRepository medRepo;


    public MedRegisterDTO getMedRegiDTOFromPayload(Map<String, Object> payload) {
        return medRegiDTOFactory.fromPayload(payload);
    }

    public Med convertToNewMed(MedRegisterDTO medRegiDTO) {
        Long visitId = getVisitIdFromVisitUuid(medRegiDTO.getVisitUuid()),
                appUserId = getAppUserIdFromAppUserUuid(medRegiDTO.getAppUserUuid());
        MedUuid medUuid = new MedUuid(UUID.randomUUID().toString());
        return medMapper.toEntity(medRegiDTO, medUuid, visitId, appUserId);
    }

    public Med save(Med med) {
        return medRepo.save(med);
    }

    public MedDTO getMedDTOFromMed(Med med) {
        AppUserUuid appUserUuid = getAppUserUuidFromAppUserId(med.getAppUserId());
        VisitUuid visitUuid = getVisitUuidFromVisitId(med.getVisitId());

        return medMapper.toDTO(med, visitUuid, appUserUuid);
    }

    public List<MedDTO> getMedDTOListByAppUserUuid(AppUserUuid appUserUuid) {
        List<Med> medList = medRepo.findAllByAppUserId(getAppUserIdFromAppUserUuid(appUserUuid));
        List<MedDTO> dtoList = new ArrayList<>();
        for (Med med : medList) {
            VisitUuid visitUuid = getVisitUuidFromVisitId(med.getVisitId());

            dtoList.add(medMapper.toDTO(med, visitUuid, appUserUuid));
        }
        return dtoList;
    }

    public MedDTO getMedDTOByMedUuid(MedUuid medUuid) {
        Med entity = medRepo.findById(getMedIdFromMedUuid(medUuid)).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication for MedUuid %s not found", medUuid))
        );

        VisitUuid visitUuid = getVisitUuidFromVisitId(entity.getVisitId());
        AppUserUuid appUserUuid = getAppUserUuidFromAppUserId(entity.getAppUserId());

        return medMapper.toDTO(entity, visitUuid, appUserUuid);
    }

    private Long getAppUserIdFromAppUserUuid(AppUserUuid appUserUuid) {
        return appUserUuid != null ? appUserCServ.getIdByUuid(appUserUuid) : null;
    }

    private Long getMedIdFromMedUuid(MedUuid medUuid) {
        return medUuid != null ? medCServ.getIdByUuid(medUuid) : null;
    }

    private Long getVisitIdFromVisitUuid(VisitUuid visitUuid) {
        return visitUuid != null ? hvCServ.getIdByUuid(visitUuid) : null;
    }

    private VisitUuid getVisitUuidFromVisitId(Long visitId) {
        return visitId != null ? hvCServ.getUuidById(visitId) : null;
    }

    private AppUserUuid getAppUserUuidFromAppUserId(Long appUserId) {
        return appUserId != null ? appUserCServ.getUuidById(appUserId) : null;
    }


}