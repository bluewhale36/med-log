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
public class EveryDayDetail extends AbstractDoseFrequencyDetail {

    private final List<DoseTimeCount> doseTimeCountList;

    @JsonCreator
    public EveryDayDetail(@JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList) {
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
                "매일 %s %s.", doseTimeCountSentence, medType.getDoseActionInVerb()
        );
    }
}
