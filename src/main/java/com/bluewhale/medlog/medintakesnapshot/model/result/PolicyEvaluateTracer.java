package com.bluewhale.medlog.medintakesnapshot.model.result;

import com.bluewhale.medlog.medintakesnapshot.model.result.reason.AbstractEvaluateReason;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PolicyEvaluateTracer {

    private boolean isPreProcessNeeded;
    private PreProcess preProcess;

    private boolean isSpecificProcessNeeded;
    private AbstractEvaluateReason reason;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class PreProcess {
        private Boolean isReferenceDateInMedDuration;
        private Boolean hasTakenRecordOnReferenceDate;
    }

    public static PolicyEvaluateTracer copyOf(PolicyEvaluateTracer aInstance) {
        if (aInstance == null) {
            throw new IllegalArgumentException("aInstance cannot be null to copy PolicyEvaluateTracer");
        }

        PolicyEvaluateTracer copy = new PolicyEvaluateTracer();

        copy.isPreProcessNeeded = aInstance.isPreProcessNeeded;
        copy.preProcess = aInstance.preProcess != null ? aInstance.preProcess : null;

        copy.isSpecificProcessNeeded = aInstance.isSpecificProcessNeeded;
        copy.reason = aInstance.reason != null ? aInstance.reason : null;

        return copy;
    }
}
