package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;

import java.time.LocalTime;
import java.util.List;

public abstract class AbstractDoseFrequencyDetail {

    public abstract List<DoseTimeCount> getDoseTimeCountList();
}