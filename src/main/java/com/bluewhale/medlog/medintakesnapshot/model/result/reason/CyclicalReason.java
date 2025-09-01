package com.bluewhale.medlog.medintakesnapshot.model.result.reason;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class CyclicalReason extends AbstractEvaluateReason {

    private final Long takenForInDays;
    private final Long cycleCount;
    private final Long dayN;
    private final boolean isSnapshotDateInOnDuration;

    @JsonCreator
    public CyclicalReason(
            @JsonProperty("takenForInDays") long takenForInDays,
            @JsonProperty("cycleCount") long cycleCount,
            @JsonProperty("dayN") long dayN,
            @JsonProperty("isSnapshotDateInOnDuration") boolean isSnapshotDateInOnDuration
    ) {
        this.takenForInDays = takenForInDays;
        this.cycleCount = cycleCount;
        this.dayN = dayN;
        this.isSnapshotDateInOnDuration = isSnapshotDateInOnDuration;
    }

    @Override
    public DoseFrequencyType getDoseFrequencyType() {
        return DoseFrequencyType.CYCLICAL;
    }

}
