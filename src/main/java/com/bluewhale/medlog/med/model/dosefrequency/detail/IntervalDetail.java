package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class IntervalDetail extends AbstractDoseFrequencyDetail {

    private final Integer interval;
    private final List<DoseTimeCount> doseTimeCountList;

    @JsonCreator
    public IntervalDetail(
            @JsonProperty("interval") Integer interval,
            @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList
    ) {
        this.interval = interval;
        this.doseTimeCountList = doseTimeCountList;
    }

    @Override
    protected String doGenerateDetailFrequencySentence(MedType medType) {
        List<String> doseTimeCountSentenceList = new ArrayList<>();
        for (DoseTimeCount doseTimeCount : doseTimeCountList) {
            doseTimeCountSentenceList.add(
                    String.format(
                            "%s분에 %s%s", doseTimeCount.getDoseTime(), doseTimeCount.getDoseCount(), medType.getCountUnit()
                    )
            );
        }
        String doseTimeCountSentence = String.join(", ", doseTimeCountSentenceList);

        return String.format(
                "%s일 간격으로 %s. %s기간 중에는 %s %s.",
                interval, medType.getDoseActionInVerb(),
                medType.getDoseActionInNoun(), doseTimeCountSentence, medType.getDoseActionInVerb()
        );
    }
}
