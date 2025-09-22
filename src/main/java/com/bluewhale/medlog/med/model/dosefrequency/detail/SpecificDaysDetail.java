package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.MedType;
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

    @Override
    protected String doGenerateDetailFrequencySentence(MedType medType) {
        List<String> daySetStringList = new ArrayList<>();
        for (int i = 0; i < specificDays.size(); i++) {
            SpecificDaysSet daySet = specificDays.get(i);

            String dayString = String.join(", ", daySet.getDayList().stream().map(Days::getTitle).toList());

            List<String> doseTimeCountSentenceList = new ArrayList<>();
            for (DoseTimeCount doseTimeCount : daySet.getDoseTimeCountList()) {
                doseTimeCountSentenceList.add(
                        String.format(
                                "%s분에 %s%s", doseTimeCount.getDoseTime(), doseTimeCount.getDoseCount(), medType.getCountUnit()
                        )
                );
            }
            String doseTimeCountSentence = String.join(", ", doseTimeCountSentenceList);

            daySetStringList.add(
                    String.format("%d. %s : %s %s.\n", i +1, dayString, doseTimeCountSentence, medType.getDoseActionInVerb())
            );
        }

        return String.join("", daySetStringList);
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
