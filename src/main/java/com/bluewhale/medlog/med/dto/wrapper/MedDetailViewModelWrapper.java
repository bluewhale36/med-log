package com.bluewhale.medlog.med.dto.wrapper;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedDetailViewModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class MedDetailViewModelWrapper {

    Map<MedUuid, MedDetailViewModel> medDetailViewModelMap;

    @JsonCreator
    public MedDetailViewModelWrapper(
            @JsonProperty("medDetailViewModelMap")
            Map<MedUuid, MedDetailViewModel> medDetailViewModelMap
    ) {
        this.medDetailViewModelMap = medDetailViewModelMap;
    }
}
