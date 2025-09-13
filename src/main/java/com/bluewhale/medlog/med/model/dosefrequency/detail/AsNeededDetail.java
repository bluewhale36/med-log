package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class AsNeededDetail extends AbstractDoseFrequencyDetail {

    @JsonProperty("asNeeded")
    private final boolean asNeeded = true;


    @Override
    public String humanReadable() {
        return "필요할 때마다 복용하세요.";
    }

    @Override
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        return Optional.empty();
    }
}
