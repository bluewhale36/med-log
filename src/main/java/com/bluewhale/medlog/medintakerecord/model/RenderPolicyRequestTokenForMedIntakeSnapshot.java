package com.bluewhale.medlog.medintakerecord.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record RenderPolicyRequestTokenForMedIntakeSnapshot(Long medIntakeSnapshotId, Long medId,
                                                           LocalDateTime estimatedDoseTime) {

    public LocalDate getEstimatedDoseTimeAsLocalDate() {
        return estimatedDoseTime.toLocalDate();
    }

    public LocalTime getEstimatedDoseTimeAsLocalTime() {
        return estimatedDoseTime.toLocalTime();
    }
}
