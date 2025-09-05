package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordConvertService_Impl;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.factory.MedRegisterDTOFactory;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedService {

    private final MedRegisterDTOFactory medRegiDTOFactory;

    private final HospitalVisitRecordConvertService_Impl hvCServ;
    private final AppUserConvertService_Impl appUserCServ;
    private final MedConvertService_Impl medCServ;

    private final MedRepository medRepo;

    private final AppUserRepository appUserRepository;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;


    public MedRegisterDTO getMedRegiDTOFromPayload(Map<String, Object> payload) {
        return medRegiDTOFactory.fromPayload(payload);
    }

    public Med convertToNewMed(MedRegisterDTO medRegiDTO) {
        AppUser appUserReference = appUserRepository.getReferenceById(appUserCServ.getIdByUuid(medRegiDTO.getAppUserUuid()));
        HospitalVisitRecord hospitalVisitRecordReference =
                medRegiDTO.getVisitUuid() != null ?
                hospitalVisitRecordRepository.getReferenceById(hvCServ.getIdByUuid(medRegiDTO.getVisitUuid())) : null;

        return Med.create(medRegiDTO, appUserReference, hospitalVisitRecordReference);
    }


    public Med save(Med med) {
        return medRepo.save(med);
    }

    public MedDTO getMedDTOFromMed(Med med) {
        return MedDTO.from(med);
    }

    public List<MedDTO> getMedDTOListByAppUserUuid(AppUserUuid appUserUuid) {
        List<Med> medList = medRepo.findAllByAppUserId(appUserCServ.getIdByUuid(appUserUuid));
        return medList.stream().map(MedDTO::from).toList();
    }

    public MedDTO getMedDTOByMedUuid(MedUuid medUuid) {
        Med entity = medRepo.findById(medCServ.getIdByUuid(medUuid)).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication for MedUuid %s not found", medUuid))
        );
        return MedDTO.from(entity);
    }


}