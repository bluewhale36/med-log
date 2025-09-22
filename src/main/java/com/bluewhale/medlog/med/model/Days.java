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

    MON("월요일", "Monday", DayOfWeek.MONDAY),
    TUE("화요일", "Tuesday", DayOfWeek.TUESDAY),
    WED("수요일", "Wednesday", DayOfWeek.WEDNESDAY),
    THU("목요일", "Thursday", DayOfWeek.THURSDAY),
    FRI("금요일", "Friday", DayOfWeek.FRIDAY),
    SAT("토요일", "Saturday", DayOfWeek.SATURDAY),
    SUN("일요일", "Sunday", DayOfWeek.SUNDAY);

    private final String title;
    private final String value;
    private final DayOfWeek inDayOfWeek;


    public static Days fromDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(Days.values()).filter(e -> e.inDayOfWeek.equals(dayOfWeek)).findFirst().orElse(null);
    }

}
