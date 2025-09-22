package com.bluewhale.medlog.med.model.dosefrequency.detail.timecount;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
public class DoseTimeCount {

    private final LocalTime doseTime;
    private final int doseCount;

    public DoseTimeCount(LocalTime doseTime, int doseCount) {
        this.doseTime = doseTime;
        this.doseCount = doseCount;
    }
}
