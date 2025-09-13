package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        return Optional.empty();
    }

    @Getter
    @ToString
    public static class SpecificDaysSet {

        private final List<Days> days;
        private final List<DoseTimeCount> doseTimeCountList;

        @JsonCreator
        public SpecificDaysSet(
                               @JsonProperty("days") List<Days> days,
                               @JsonProperty("times") List<DoseTimeCount> doseTimeCountList
        ) {
            this.days = days;
            this.doseTimeCountList = doseTimeCountList;
        }

        private String humanReadable(Function<List<LocalTime>, String> timeFormatter) {
            String daysStr = String.join("요일, ", days.stream().map(Days::getTitle).toArray(String[]::new)) + "요일";
            String timesStr = timeFormatter.apply(this.doseTimeCountList);
            return String.format("%s마다 %s에 복용합니다.", daysStr, timesStr);
        }

    }
}
