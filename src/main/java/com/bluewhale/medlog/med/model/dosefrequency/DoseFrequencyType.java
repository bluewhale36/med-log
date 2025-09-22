package com.bluewhale.medlog.med.model.dosefrequency;

import com.bluewhale.medlog.med.model.dosefrequency.detail.*;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum DoseFrequencyType {

    private final String title;
    private final String placeholder;
    private final Class<? extends AbstractDoseFrequencyDetail> detailClass;
    private final Class<? extends AbstractEvaluateReason> reasonClass;

    public boolean supportsDetailClass(Class<? extends AbstractDoseFrequencyDetail> type) {
        return detailClass.isAssignableFrom(type);
    }

    public boolean supportsReasonClass(Class<? extends AbstractEvaluateReason> type) {
        return reasonClass.isAssignableFrom(type);
    }

