package com.bluewhale.medlog.medintakerecord.model;

import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;

import java.time.LocalDate;
import java.time.LocalTime;

public record RenderPolicyResultToken(
        Long medId, LocalDate referenceDate, LocalTime referenceTime, RecordViewType recordViewType
) {
}
