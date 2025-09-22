package com.bluewhale.medlog.med.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@ToString
public enum Days {

    private final String title;
    private final String value;
    private final DayOfWeek inDayOfWeek;


    public static Days fromDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(Days.values()).filter(e -> e.inDayOfWeek.equals(dayOfWeek)).findFirst().orElse(null);
    }

}
