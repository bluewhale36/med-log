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
    private final List<LocalTime> times;

    private final Integer onDurationInDays;
    private final Integer offDurationInDays;

    @JsonCreator
    public CyclicalDetail(
            @JsonProperty("times") List<LocalTime> times,
            @JsonProperty("onDuration") int onDuration,
            @JsonProperty("offDuration") int offDuration,
            @JsonProperty("cycleUnit") CycleUnit cycleUnit
    ) {
        this.times = times;
        this.onDuration = onDuration;
        this.offDuration = offDuration;
        this.cycleUnit = cycleUnit;


        this.onDurationInDays = cycleUnit.getDurationAsDays(onDuration);
        this.offDurationInDays = cycleUnit.getDurationAsDays(offDuration);
    }

    @Override
    public String humanReadable() {
        return String.format(
                "%d일간 복용하고, %d일간 복용하지 않습니다.\n복용 기간에는 %s에 복용합니다.",
                onDurationInDays, offDurationInDays, humanReadableTimeListAsString(times)
        );
    }
}
