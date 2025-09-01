package com.bluewhale.medlog.appuser.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Getter
@ToString
public enum HeightUnit {

    CENTIMETER("cm", x -> x),
    METER("m", x -> x /100),
    INCHES("in", x -> x *2.54f),
    FEET("ft", x -> x * 30.48f);

    private final String title;
    private final UnaryOperator<Float> operator;

    public Float toCentimeters(Float value) {
        return operator.apply(value);
    }
}
