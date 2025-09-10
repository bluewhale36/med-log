package com.bluewhale.medlog.med.model.dosefrequency.detail;

import java.time.LocalTime;
import java.util.List;

public abstract class AbstractDoseFrequencyDetail {

    public abstract String humanReadable();

    private String[] humanReadableTimeListStringSequence(List<LocalTime> times) {
        return times.stream().map(LocalTime::toString).toArray(String[]::new);
    }

    protected String humanReadableTimeListAsString(List<LocalTime> times) {
        return String.join("분, ", humanReadableTimeListStringSequence(times)) + "분";
    }
}