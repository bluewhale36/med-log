package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.MedType;
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

    @Override
    protected String doGenerateDetailFrequencySentence(MedType medType) {
        return String.format("필요에 따라 %s.", medType.getDoseActionInVerb());
    }


}
