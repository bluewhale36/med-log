package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsNeededDetail extends AbstractDoseFrequencyDetail {

    @JsonProperty("asNeeded")
    private final boolean asNeeded = true;


    @Override
    public String humanReadable() {
        return "필요할 때마다 복용하세요.";
    }
}
