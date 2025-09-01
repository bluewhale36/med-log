package com.bluewhale.medlog.appuser.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Getter
@ToString
public enum WeightUnit {

    KILOGRAM("kg", x -> x),
    POUND("lb", x -> x *0.45359237f);

    private final String title;
    private final UnaryOperator<Float> operator;

    public Float toKilograms(Float value) {
        return operator.apply(value);
    }
}
