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
    public EveryDayDetail(@JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList) {
        this.doseTimeCountList = doseTimeCountList;
    }

}
