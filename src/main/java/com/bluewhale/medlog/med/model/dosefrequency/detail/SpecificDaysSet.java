package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@ToString
public class SpecificDaysSet {

    private final List<Days> days;
    private final List<LocalTime> times;

    @JsonCreator
    public SpecificDaysSet(
            @JsonProperty("days") List<Days> days,
            @JsonProperty("times") List<LocalTime> times
    ) {
        this.days = days;
        this.times = times;
    }
}
