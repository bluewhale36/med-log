package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.CycleUnit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;


@Getter
@ToString
public class CyclicalDetail extends AbstractDoseFrequencyDetail {

    private final CycleUnit cycleUnit;
    private final Integer onDuration;
    private final Integer offDuration;
    private final List<DoseTimeCount> doseTimeCountList;

    private final Integer onDurationInDays;
    private final Integer offDurationInDays;

    @JsonCreator
    public CyclicalDetail(
            @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList,
            @JsonProperty("onDuration") int onDuration,
            @JsonProperty("offDuration") int offDuration,
            @JsonProperty("cycleUnit") CycleUnit cycleUnit
    ) {
        this.doseTimeCountList = doseTimeCountList;
        this.onDuration = onDuration;
        this.offDuration = offDuration;
        this.cycleUnit = cycleUnit;


        this.onDurationInDays = cycleUnit.getDurationAsDays(onDuration);
        this.offDurationInDays = cycleUnit.getDurationAsDays(offDuration);
    }

    @Override
        List<LocalTime> timeList = doseTimeCountList.stream().map(DoseTimeCount::getDoseTime).toList();
        return String.format(
        );
    }
}
