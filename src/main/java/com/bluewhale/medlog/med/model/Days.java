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
    SUN("일", "Sunday", DayOfWeek.SUNDAY),
    MON("월", "Monday", DayOfWeek.MONDAY),
    TUE("화", "Tuesday", DayOfWeek.TUESDAY),
    WED("수", "Wednesday", DayOfWeek.WEDNESDAY),
    THU("목", "Thursday", DayOfWeek.THURSDAY),
    FRI("금", "Friday", DayOfWeek.FRIDAY),
    SAT("토", "Saturday", DayOfWeek.SATURDAY),;

    private final String title;
    private final String value;
    private final DayOfWeek inDayOfWeek;


    public static Days fromDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(Days.values()).filter(e -> e.inDayOfWeek.equals(dayOfWeek)).findFirst().orElse(null);
    }

}
