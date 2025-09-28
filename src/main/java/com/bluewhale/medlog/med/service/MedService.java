package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordIdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedDTO;
import com.bluewhale.medlog.med.dto.dto.MedDetailViewModel;
import com.bluewhale.medlog.med.dto.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.parser.MedRegisterPayloadParser;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedService {

    private final MedRegisterPayloadParser medRegisterPayloadParser;

    private final HospitalVisitRecordIdentifierConvertService hvCServ;
    private final AppUserIdentifierConvertService appUserCServ;
    private final MedIdentifierConvertService medCServ;

    private final MedRepository medRepo;

    private final AppUserRepository appUserRepository;
    private final HospitalVisitRecordRepository hospitalVisitRecordRepository;


    public MedRegisterDTO getMedRegisterDTOFromPayload(Map<String, Object> payload) {
        return medRegisterPayloadParser.parseData(payload);
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

    @CachePut(key = "#entity.medUuid", value = "medDetailViewModel")
    public MedDetailViewModel cachePutMedDetailViewModel(Med entity) {
        return MedDetailViewModel.from(entity);
    }


}