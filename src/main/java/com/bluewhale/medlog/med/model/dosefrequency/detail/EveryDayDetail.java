package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@ToString
public class EveryDayDetail extends AbstractDoseFrequencyDetail {

    private final List<LocalTime> times;

    @JsonCreator
    public EveryDayDetail(@JsonProperty("times") List<LocalTime> times) {
        this.times = times;
    }


    @Override
    public String humanReadable() {
        return "매일 " + humanReadbleTimeListAsString(times) + "에 복용합니다.";
    }
}
