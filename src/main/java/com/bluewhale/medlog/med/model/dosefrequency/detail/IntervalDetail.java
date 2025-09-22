package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
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
    private final List<DoseTimeCount> doseTimeCountList;

    @JsonCreator
    public IntervalDetail(
            @JsonProperty("interval") Integer interval,
            @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList
    ) {
        this.interval = interval;
        this.doseTimeCountList = doseTimeCountList;
    }

}
