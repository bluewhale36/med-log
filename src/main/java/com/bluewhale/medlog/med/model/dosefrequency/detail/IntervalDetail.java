package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

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
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        return Optional.of(doseTimeCountList);
    }

    @Override
    public String getHumanReadableFirstSentence() {
        return String.format("매 %d일에 한번 복용합니다.", interval);
    }
}
