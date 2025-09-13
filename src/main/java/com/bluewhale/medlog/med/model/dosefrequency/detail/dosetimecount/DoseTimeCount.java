package com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class DoseTimeCount {

    private final LocalTime doseTime;
    private final byte count;
}
