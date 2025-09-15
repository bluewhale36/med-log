package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AsNeededDetail extends AbstractDoseFrequencyDetail {

    @JsonProperty("asNeeded")
    private final boolean asNeeded = true;


    @Override
    public String humanReadable() {
        return "필요할 때마다 복용하세요.";
    }

    @Override
    public List<DoseTimeCount> getDoseTimeCountList() {
        return new ArrayList<DoseTimeCount>();
    }
}
