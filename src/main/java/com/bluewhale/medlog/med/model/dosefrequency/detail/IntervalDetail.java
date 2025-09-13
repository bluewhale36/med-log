package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class IntervalDetail extends AbstractDoseFrequencyDetail {

    private final Integer interval;
    private final List<DoseTimeCount> doseTimeCountList;

    @JsonCreator
    public IntervalDetail(
            @JsonProperty("interval") Integer interval,
            @JsonProperty("times") List<DoseTimeCount> doseTimeCountList
    ) {
        this.interval = interval;
        this.doseTimeCountList = doseTimeCountList;
    }

    @Override
    public String humanReadable() {
        return interval + "일에 하루 복용합니다. 복용일의 복용 시각은 " + humanReadableTimeListAsString(doseTimeCountList) + " 입니다.";
    }
}
