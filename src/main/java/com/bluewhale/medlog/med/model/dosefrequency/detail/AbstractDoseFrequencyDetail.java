package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDoseFrequencyDetail {

    public abstract String humanReadable();

    private String[] humanReadableTimeListStringSequence(List<LocalTime> times) {
        return times.stream().map(LocalTime::toString).toArray(String[]::new);
    }

    protected String humanReadableTimeListAsString(List<LocalTime> times) {
        return String.join("분, ", humanReadableTimeListStringSequence(times)) + "분";
    }

    public abstract Optional<List<DoseTimeCount>> doseTimeCountList();
}