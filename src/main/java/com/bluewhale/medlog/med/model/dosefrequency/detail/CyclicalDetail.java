package com.bluewhale.medlog.med.model.dosefrequency.detail;

import com.bluewhale.medlog.med.model.CycleUnit;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;


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
            @JsonProperty("times") List<DoseTimeCount> doseTimeCountList,
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
    public Optional<List<DoseTimeCount>> doseTimeCountList() {
        return Optional.of(doseTimeCountList);
    }

    @Override
    public String getHumanReadableFirstSentence() {
        return String.format(
                "%d%s 간 복용하고 %d%s 간 복용을 중단합니다.",
                onDuration, cycleUnit.getInKorean(),
                offDuration, cycleUnit.getInKorean()
        );
    }
}
