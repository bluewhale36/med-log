package com.bluewhale.medlog.medintakesnapshot.model.result.reason;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@ToString
public class EveryDayReason extends AbstractEvaluateReason {

    @Override
    public DoseFrequencyType getDoseFrequencyType() {
        return DoseFrequencyType.EVERY_DAY;
    }


}
