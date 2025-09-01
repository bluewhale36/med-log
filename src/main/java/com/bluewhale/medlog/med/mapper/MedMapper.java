package com.bluewhale.medlog.med.mapper;


import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MedMapper {

    public Med toEntity(MedDTO dto, Long medId, Long visitId, Long appUserId) {
        if (medId == null) {
            throw new IllegalArgumentException("MedId must not be null");
        }
        return Med.builder()
                .medId(medId)
                .medUuid(dto.getMedUuid())
                .visitId(visitId)
                .appUserId(appUserId)
                .medName(dto.getMedName())
                .medType(dto.getMedType())
                .doseAmount(dto.getDoseAmount())
                .doseUnit(dto.getDoseUnit())
                .doseFrequency(dto.getDoseFrequency())
                .instruction(dto.getInstruction())
                .effect(dto.getEffect())
                .sideEffect(dto.getSideEffect())
                .startedOn(dto.getStartedOn())
                .endedOn(dto.getEndedOn())
                .build();
    }

    public Med toEntity(MedRegisterDTO regiDTO, MedUuid medUuid, Long visitId, Long appUserId) {
        return Med.builder()
                .medId(null)
                .medUuid(medUuid)
                .visitId(visitId)
                .appUserId(appUserId)
                .medName(regiDTO.getMedName())
                .medType(regiDTO.getMedType())
                .doseAmount(regiDTO.getDoseAmount())
                .doseUnit(regiDTO.getDoseUnit())
                .doseFrequency(regiDTO.getDoseFrequency())
                .instruction(regiDTO.getInstruction())
                .effect(regiDTO.getEffect())
                .sideEffect(regiDTO.getSideEffect())
                .startedOn(regiDTO.getStartedOn())
                .endedOn(regiDTO.getEndedOn())
                .build();
    }

    public MedDTO toDTO(Med entity, VisitUuid visitUuid, AppUserUuid appUserUuid) {
        return new MedDTO(
                entity.getMedUuid(),
                visitUuid,
                appUserUuid,
                entity.getMedName(),
                entity.getMedType(),
                entity.getDoseAmount(),
                entity.getDoseUnit(),
                entity.getDoseFrequency(),
                entity.getInstruction(),
                entity.getEffect(),
                entity.getSideEffect(),
                entity.getStartedOn(),
                entity.getEndedOn()
        );
    }

    public MedAggregationDTO toFullDTO(Med entity, VisitUuid visitUuid, AppUserUuid appUserUuid, List<MedIntakeRecordAggregationDTO> mirFullDTOList) {
        return MedAggregationDTO.of(
                entity.getMedId(), entity.getMedUuid(),
                entity.getVisitId(), visitUuid,
                entity.getAppUserId(), appUserUuid,
                entity.getMedName(), entity.getMedType(), entity.getDoseAmount(), entity.getDoseUnit(), entity.getDoseFrequency(),
                entity.getEffect(), entity.getSideEffect(), entity.getStartedOn(), entity.getEndedOn(),
                mirFullDTOList
        );
    }

}
