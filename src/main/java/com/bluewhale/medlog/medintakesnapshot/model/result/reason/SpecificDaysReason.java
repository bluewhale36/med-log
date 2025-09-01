package com.bluewhale.medlog.medintakesnapshot.model.result.reason;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class SpecificDaysReason extends AbstractEvaluateReason {

    private final Days dayOfStdDate;
    private final boolean isThereAnySetOnDayOfStdDate;

    @JsonCreator
    public SpecificDaysReason(
            @JsonProperty("dayOfStdDate") Days dayOfStdDate,
            @JsonProperty("isThereAnySetonDayOfStdDate") boolean isThereAnySetOnDayOfStdDate
    ) {
        this.dayOfStdDate = dayOfStdDate;
        this.isThereAnySetOnDayOfStdDate = isThereAnySetOnDayOfStdDate;
    }

    @Override
    public DoseFrequencyType getDoseFrequencyType() {
        return DoseFrequencyType.SPECIFIC_DAYS;
    }
}
