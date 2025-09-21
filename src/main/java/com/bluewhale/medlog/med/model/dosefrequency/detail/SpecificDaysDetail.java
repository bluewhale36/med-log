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
        List<String> dayInKoreanList = specificDays.stream()
                .map(SpecificDaysSet::getDays)
                .flatMap(List::stream)
                .map(Days::getTitle)
                .toList();

        // TODO : 평일, 주말 여부 등 고려해서 조금 더 세부적인 문자열 생성 로직 구현 필요.
        return "매주" + String.join("요일, ", dayInKoreanList) + "에 복용합니다.";
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
    }
}
