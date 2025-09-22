package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.CycleUnit;
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
public class CyclicalDetail extends AbstractDoseFrequencyDetail {

    private final CycleUnit cycleUnit;
    private final Integer onDuration;
    private final Integer offDuration;
    private final List<DoseTimeCount> doseTimeCountList;

    private final Integer onDurationInDays;
    private final Integer offDurationInDays;

    @JsonCreator
    public CyclicalDetail(
            @JsonProperty("doseTimeCountList") List<DoseTimeCount> doseTimeCountList,
            @JsonProperty("onDuration") int onDuration,
            @JsonProperty("offDuration") int offDuration,
            @JsonProperty("cycleUnit") CycleUnit cycleUnit
    ) {
        this.doseTimeCountList = doseTimeCountList;
        this.onDuration = onDuration;
        this.offDuration = offDuration;
        this.cycleUnit = cycleUnit;


        this.onDurationInDays = cycleUnit.getDurationAsDays(onDuration);
        this.offDurationInDays = cycleUnit.getDurationAsDays(offDuration);
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
                "%d%s간 %s하고 %d%s간 중단합니다.\n%s 기간 중에는 %s %s.",
                onDuration, cycleUnit.getUnitInKorean(), medType.getDoseActionInNoun(),
                offDuration, cycleUnit.getUnitInKorean(),
                medType.getDoseActionInNoun(), doseTimeCountSentence, medType.getDoseActionInVerb()
        );
    }
}
