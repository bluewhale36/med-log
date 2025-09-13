package com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
public class DoseTimeCount {

    private final LocalTime doseTime;
    private final byte doseCount;

    @JsonCreator
    public DoseTimeCount(
            @JsonProperty("doseTime") LocalTime doseTime,
            @JsonProperty("doseCount") byte doseCount
    ) {
        this.doseTime = doseTime;
        this.doseCount = doseCount;
    }
}
