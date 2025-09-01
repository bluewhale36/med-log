package com.bluewhale.medlog.medintakesnapshot.model.result.reason;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import lombok.ToString;

import java.util.Map;

@ToString
public class AsNeededReason extends AbstractEvaluateReason {

    @Override
    public DoseFrequencyType getDoseFrequencyType() {
        return DoseFrequencyType.AS_NEEDED;
    }

}
