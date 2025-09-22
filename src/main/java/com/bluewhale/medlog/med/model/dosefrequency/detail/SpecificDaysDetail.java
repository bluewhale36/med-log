package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.ArrayList;
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
    public List<DoseTimeCount> getDoseTimeCountList() {
        List<DoseTimeCount> list = new ArrayList<>();
        for (SpecificDaysSet set : specificDays) {
            list.addAll(set.getDoseTimeCountList());
        }
        return list;
    }

    // SpecificDaysDetail.java 의 SpecificDaysSet 클래스 (수정된 코드)
    @Getter
    @ToString
    public static class SpecificDaysSet {

        private final List<Days> dayList;
        private final List<DoseTimeCount> doseTimeCountList;

        @JsonCreator
        public SpecificDaysSet(
                @JsonProperty("dayList") List<Days> dayList,
                @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList
        ) {
            this.dayList = dayList;
            this.doseTimeCountList = doseTimeCountList;
        }

    }
}
