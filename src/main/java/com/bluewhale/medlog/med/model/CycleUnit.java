package com.bluewhale.medlog.med.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.IntUnaryOperator;

@RequiredArgsConstructor
@Getter
@ToString
public enum CycleUnit {


    private final String title;
    private final IntUnaryOperator operator;

    public Integer getDurationAsDays(int value) {
        return operator.applyAsInt(value);
    }

}
