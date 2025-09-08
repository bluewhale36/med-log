package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordIdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.service.MedIntakeRecordAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedAggregationService {

    private final MedRepository medRepo;


    private final HospitalVisitRecordIdentifierConvertService hvrQServ;
    private final AppUserIdentifierConvertService appUserQServ;

    private final MedIntakeRecordAggregationService mirFServ;

    public List<MedAggregationDTO> getMedAggDTOListByAppUserId(Long appUserId) {
        return medRepo.findAllByAppUserId(appUserId).stream()
                .map(Med::getMedId)
                .map(this::getMedAggDTOByMedId)
                .toList();
    }

    public MedAggregationDTO getMedAggDTOByMedId(Long medId) {
        Med entity = medRepo.findById(medId).orElseThrow(
                () -> new IllegalArgumentException(String.format("Medication for MedUuid %s not found", medId))
        );
        VisitUuid visitUuid =
                entity.getHospitalVisitRecord() != null ?
                hvrQServ.getUuidById(entity.getHospitalVisitRecord().getVisitId()) :
                null;
        AppUserUuid appUserUuid = appUserQServ.getUuidById(entity.getAppUser().getAppUserId());
        List<MedIntakeRecordAggregationDTO> mirFullDTOList = mirFServ.getMirFullDTOListByMedId(medId);

        return toAggregationDTO(entity, mirFullDTOList);
    }

    private MedAggregationDTO toAggregationDTO(Med entity, List<MedIntakeRecordAggregationDTO> mirFullDTOList) {
        boolean hasVisitRecord = entity.getHospitalVisitRecord() != null;
        return MedAggregationDTO.of(
                entity.getMedId(), entity.getMedUuid(),
                hasVisitRecord ? entity.getHospitalVisitRecord().getVisitId() : null, hasVisitRecord ? entity.getHospitalVisitRecord().getVisitUuid() :  null,
                entity.getAppUser().getAppUserId(), entity.getAppUser().getAppUserUuid(),
                entity.getMedName(), entity.getMedType(),
                entity.getDoseAmount(), entity.getDoseUnit(), entity.getDoseFrequency(),
                entity.getEffect(), entity.getSideEffect(),
                entity.getStartedOn(), entity.getEndedOn(),
                mirFullDTOList
        );
    }
}
