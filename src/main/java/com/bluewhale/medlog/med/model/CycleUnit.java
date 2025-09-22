package com.bluewhale.medlog.med.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.IntUnaryOperator;

@RequiredArgsConstructor
@Getter
@ToString
public enum CycleUnit {

    DAY("day", x -> x),
    WEEK("week", x -> x *7);

    private final String title;
    private final IntUnaryOperator operator;

    public Integer getDurationAsDays(int value) {
        return operator.applyAsInt(value);
    }

}
