package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class AsNeededDetail extends AbstractDoseFrequencyDetail {

    @JsonProperty("asNeeded")
    private final boolean asNeeded = true;

    @Override
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        return Optional.empty();
    }

    @Override
    public String getHumanReadableFirstSentence() {
        return "필요에 따라 복용합니다.";
    }
}
