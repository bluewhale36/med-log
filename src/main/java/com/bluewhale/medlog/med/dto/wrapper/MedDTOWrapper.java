package com.bluewhale.medlog.med.dto.wrapper;

import com.bluewhale.medlog.med.dto.dto.MedDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class MedDTOWrapper {

    private final List<MedDTO> medDTOList;

    @JsonCreator
    public MedDTOWrapper(@JsonProperty("medDTOList")  List<MedDTO> medDTOList) {
        this.medDTOList = medDTOList;
    }

    public static MedDTOWrapper of(List<MedDTO> dtoList) {
        return new MedDTOWrapper(dtoList);
    }
}
