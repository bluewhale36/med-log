package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDoseFrequencyDetail {

    public abstract Optional<List<DoseTimeCount>> doseTimeCountList();

    public abstract String getHumanReadableFirstSentence();
}