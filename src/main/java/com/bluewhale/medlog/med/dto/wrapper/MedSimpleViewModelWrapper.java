package com.bluewhale.medlog.med.dto.wrapper;

import com.bluewhale.medlog.med.dto.dto.MedSimpleViewModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MedSimpleViewModelWrapper {

    private final List<MedSimpleViewModel> medSimpleViewModelList;

    @JsonCreator
    public MedSimpleViewModelWrapper(
            @JsonProperty("medSimpleViewModelList")
            List<MedSimpleViewModel> medSimpleViewModelList
    ) {
        this.medSimpleViewModelList = medSimpleViewModelList;
    }
}
