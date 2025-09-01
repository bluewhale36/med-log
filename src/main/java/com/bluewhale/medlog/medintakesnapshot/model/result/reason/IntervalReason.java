package com.bluewhale.medlog.medintakesnapshot.model.result.reason;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@ToString
public class IntervalReason extends AbstractEvaluateReason {

    private final Long takenForInDays;
    private final Long takenCount;
    private final Long intervalDayN;

    private final LocalDate previousDoseDate;
    private final LocalDate nextDoseDate;

    private final boolean isSnapshotDateOnDoseDate;

    @JsonCreator
    public IntervalReason(
            @JsonProperty("takenForInDays") long takenForInDays,
            @JsonProperty("takenCount") long takenCount,
            @JsonProperty("intervalDayN") long intervalDayN,
            @JsonProperty("previousDoseDate") LocalDate previousDoseDate,
            @JsonProperty("nextDoseDate") LocalDate nextDoseDate,
            @JsonProperty("isSnapshotDateOnDoseDate") boolean isSnapshotDateOnDoseDate
    ) {
        this.takenForInDays = takenForInDays;
        this.takenCount = takenCount;
        this.intervalDayN = intervalDayN;
        this.previousDoseDate = previousDoseDate;
        this.nextDoseDate = nextDoseDate;
        this.isSnapshotDateOnDoseDate = isSnapshotDateOnDoseDate;
    }

    @Override
    public DoseFrequencyType getDoseFrequencyType() {
        return DoseFrequencyType.INTERVAL;
    }


}
