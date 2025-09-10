package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@ToString
public class IntervalDetail extends AbstractDoseFrequencyDetail {

    private final Integer interval;
    private final List<LocalTime> times;

    @JsonCreator
    public IntervalDetail(
            @JsonProperty("interval") Integer interval,
            @JsonProperty("times") List<LocalTime> times
    ) {
        this.interval = interval;
        this.times = times;
    }

    @Override
    public String humanReadable() {
        return interval + "일에 하루 복용합니다. 복용일의 복용 시각은 " + humanReadableTimeListAsString(times) + " 입니다.";
    }
}
