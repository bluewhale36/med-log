package com.bluewhale.medlog.medintakerecord.factory;

import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyResultToken;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class MedIntakeRecordDayViewDTOFactory {


    public MedIntakeRecordDayViewDTO createMedIntakeRecordDayViewDTO(
            LocalDate stdDate,
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO>> visitItemDTOListMapForTypeRecord,
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO>> visitItemDTOListMapForTypeScheduled
    ) {
        return MedIntakeRecordDayViewDTO.of(stdDate, visitItemDTOListMapForTypeRecord, visitItemDTOListMapForTypeScheduled);
    }


    public MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO createMirViewItemDTOAsRecordType(
            RenderPolicyResultToken rpResTkn, MedAggregationDTO medAggDTO, MedIntakeRecordAggregationDTO mirAggDTO
    ) {
        return new MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO(
                RecordViewType.RECORD,
                Optional.of(mirAggDTO.getMedIntakeRecordUuid()),
                medAggDTO.getMedUuid(),
                medAggDTO.getMedName(),
                medAggDTO.getMedType(),
                medAggDTO.getDoseAmount(),
                medAggDTO.getDoseUnit(),
                mirAggDTO.isTaken(),
                LocalDateTime.of(rpResTkn.stdDate(), rpResTkn.stdTime())
        );
    }

    public MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO createMirViewItemDTOAsScheduledType(
            RenderPolicyResultToken rpResTkn, MedAggregationDTO medAggDTO
    ) {
        return new MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO(
                RecordViewType.SCHEDULED,
                Optional.empty(),
                medAggDTO.getMedUuid(),
                medAggDTO.getMedName(),
                medAggDTO.getMedType(),
                medAggDTO.getDoseAmount(),
                medAggDTO.getDoseUnit(),
                false,
                LocalDateTime.of(rpResTkn.stdDate(), rpResTkn.stdTime())
        );
    }


}
