package com.bluewhale.medlog.medintakesnapshot.model.result.reason;


import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class AbstractEvaluateReason {

    public abstract DoseFrequencyType getDoseFrequencyType();

}
