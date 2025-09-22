package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;


@Getter
@ToString
public class SpecificDaysDetail extends AbstractDoseFrequencyDetail {

    private final List<SpecificDaysSet> specificDays;

    @JsonCreator
    public SpecificDaysDetail(@JsonProperty("specificDays") List<SpecificDaysSet> specificDays) {
        this.specificDays = specificDays;
    }

    @Override
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        List<DoseTimeCount> doseTimeCountList;
        try {
            doseTimeCountList = specificDays.stream()
                    .map(SpecificDaysSet::getDoseTimeCountList)
                    .flatMap(List::stream)
                    .toList();
        } catch (NullPointerException e) {
            doseTimeCountList = null;
        }

        return doseTimeCountList == null ? Optional.empty() : Optional.of(doseTimeCountList);
    }

    @Override
    public String getHumanReadableFirstSentence() {
        List<Days> dayList = specificDays.isEmpty() ?
                null :
                specificDays.stream().map(SpecificDaysSet::getDaysList).flatMap(List::stream).toList();

        String dayStr = "";
        if (dayList != null) {
            if (dayList.size() == 2 && dayList.containsAll(List.of(Days.SAT, Days.SUN))) {
                dayStr = "매주 주말에 복용합니다.";
            } else if (dayList.size() == 5 && !dayList.containsAll(List.of(Days.SAT, Days.SUN))) {
                dayStr = "매주 평일에 복용합니다.";
            } else if (dayList.size() == 7) {
                dayStr = "매일 복용합니다.";
            } else {
                dayStr = "매주 " + String.join(", ", dayList.stream().map(Days::getTitle).toList()) + "에 복용합니다.";
            }
        }
        return dayStr;
    }

    @Getter
    @ToString
    public static class SpecificDaysSet {

        private final List<Days> daysList;
        private final List<DoseTimeCount> doseTimeCountList;

        @JsonCreator
        public SpecificDaysSet(
                               @JsonProperty("dayList") List<Days> daysList,
                               @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList
        ) {
            this.daysList = daysList;
            this.doseTimeCountList = doseTimeCountList;
        }
    }
}
