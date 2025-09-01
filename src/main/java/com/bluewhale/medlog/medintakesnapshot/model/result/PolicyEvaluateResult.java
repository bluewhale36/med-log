package com.bluewhale.medlog.medintakesnapshot.model.result;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class PolicyEvaluateResult {

    private final Long appUserId;
    private final Long medId;
    private final boolean shouldTake;
    private final LocalDate stdDate;
    private final LocalDateTime estimatedDoseTime;
    private final PolicyEvaluateTracer reason;
    private final LocalDateTime evaluatedAt;

    public PolicyEvaluateResult(Long appUserId, Long medId, boolean shouldTake, LocalDateTime estimatedDoseTime, LocalDate stdDate, PolicyEvaluateTracer reason) {
        this.appUserId = appUserId;
        this.medId = medId;
        this.shouldTake = shouldTake;
        this.estimatedDoseTime = estimatedDoseTime;
        this.reason = reason;
        this.stdDate = stdDate;
        this.evaluatedAt = LocalDateTime.now();
    }

    public PolicyEvaluateResult(Long appUserId, Long medId, boolean shouldTake, LocalDate stdDate, LocalDateTime estimatedDoseTime, PolicyEvaluateTracer reason, LocalDateTime evaluatedAt) {
        this.appUserId = appUserId;
        this.medId = medId;
        this.shouldTake = shouldTake;
        this.estimatedDoseTime = estimatedDoseTime;
        this.reason = reason;
        this.stdDate = stdDate;
        this.evaluatedAt = evaluatedAt;
    }

}
