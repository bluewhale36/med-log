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
public class EveryDayDetail extends AbstractDoseFrequencyDetail {

    private final List<DoseTimeCount> doseTimeCountList;

    @JsonCreator
    public EveryDayDetail(@JsonProperty("times") List<DoseTimeCount> doseTimeCountList) {
        this.doseTimeCountList = doseTimeCountList;
    }


    @Override
    public String humanReadable() {
        List<LocalTime> timeList = doseTimeCountList.stream().map(DoseTimeCount::getDoseTime).toList();
        return "매일 " + humanReadableTimeListAsString(timeList) + "에 복용합니다.";
    }
}
