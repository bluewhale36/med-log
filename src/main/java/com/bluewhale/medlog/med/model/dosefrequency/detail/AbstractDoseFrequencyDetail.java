package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.MedType;

import java.util.List;

public abstract class AbstractDoseFrequencyDetail {

    public abstract List<DoseTimeCount> getDoseTimeCountList();

    public String generateDetailFrequencySentence(MedType medType) {
        if (medType == null) {
            throw new IllegalArgumentException("medType must not be null");
        }
        return this.doGenerateDetailFrequencySentence(medType);
    }

    protected abstract String doGenerateDetailFrequencySentence(MedType medType);
}