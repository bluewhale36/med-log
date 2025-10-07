package com.bluewhale.medlog.hospital.dto.wrapper;

import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public record HospitalVisitRecordDTOWrapper(
        List<HospitalVisitRecordDTO> hospitalVisitRecordDTOList
) {

}
