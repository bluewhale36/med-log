package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;


@Getter
@ToString
public class SpecificDaysDetail extends AbstractDoseFrequencyDetail {

    private final List<SpecificDaysSet> specificDays;

    @JsonCreator
    public SpecificDaysDetail(@JsonProperty("specificDays") List<SpecificDaysSet> specificDays) {
        this.specificDays = specificDays;
    }

    @Override
    public String humanReadable() {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < specificDays.size(); i++) {
            // AbstractDoseFrequencyDetail 의 humanReadableTimeListAsString method 자체를 인자로 넘기는 아이디어!
            sbf.append(i + 1).append(". ").append(specificDays.get(i).humanReadable(this::humanReadableTimeListAsString)).append("\n");
        }
        return sbf.toString();
    }

    @Getter
    @ToString
    public static class SpecificDaysSet {

        private final List<Days> dayList;
        private final List<DoseTimeCount> doseTimeCountList;

        @JsonCreator
        public SpecificDaysSet(
                               @JsonProperty("days") List<Days> dayList,
                               @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList
        ) {
            this.dayList = dayList;
            this.doseTimeCountList = doseTimeCountList;
        }

        private String humanReadable(Function<List<LocalTime>, String> timeFormatter) {
            List<LocalTime> timeList = doseTimeCountList.stream().map(DoseTimeCount::getDoseTime).toList();
            String daysStr = String.join("요일, ", dayList.stream().map(Days::getTitle).toArray(String[]::new)) + "요일";
            String timesStr = timeFormatter.apply(timeList);
            return String.format("%s마다 %s에 복용합니다.", daysStr, timesStr);
        }

    }
}
