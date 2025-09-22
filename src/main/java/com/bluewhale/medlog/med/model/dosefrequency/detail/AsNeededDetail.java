package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AsNeededDetail extends AbstractDoseFrequencyDetail {

    @JsonProperty("asNeeded")
    private final boolean asNeeded = true;

    @Override
    public List<DoseTimeCount> getDoseTimeCountList() {
        return new ArrayList<>();
    }
}
