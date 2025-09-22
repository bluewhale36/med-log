package com.bluewhale.medlog.med.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.IntUnaryOperator;

@RequiredArgsConstructor
@Getter
@ToString
public enum CycleUnit {

    DAY("day", "일", x -> x),
    WEEK("week", "주",x -> x *7);

    private final String title;
    private final String unitInKorean;
    private final IntUnaryOperator operator;

    public Integer getDurationAsDays(int value) {
        return operator.applyAsInt(value);
    }

}
