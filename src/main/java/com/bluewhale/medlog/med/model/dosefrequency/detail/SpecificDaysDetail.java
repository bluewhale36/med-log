package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;


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
            sbf.append(i + 1).append(". ").append(specificDays.get(i).humanReadable()).append("\n");
        }
        return sbf.toString();
    }

    @Getter
    @ToString
    public class SpecificDaysSet {

        private final List<Days> days;
        private final List<LocalTime> times;

        @JsonCreator
        public SpecificDaysSet(
                @JsonProperty("days") List<Days> days,
                @JsonProperty("times") List<LocalTime> times
        ) {
            this.days = days;
            this.times = times;
        }

        private String humanReadable() {
            return String.format(
                    "%s마다 %s에 복용합니다.",
                    String.join("요일, ", days.stream().map(Days::getTitle).toArray(String[]::new)) + "요일",
                    humanReadbleTimeListAsString(times)
            );
        }

    }
}
