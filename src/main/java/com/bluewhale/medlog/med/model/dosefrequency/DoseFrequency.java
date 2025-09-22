package com.bluewhale.medlog.med.model.dosefrequency;

import com.bluewhale.medlog.med.model.dosefrequency.detail.AbstractDoseFrequencyDetail;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class DoseFrequency {
    private final DoseFrequencyType doseFrequencyType;
    private final AbstractDoseFrequencyDetail doseFrequencyDetail;

    public static DoseFrequency of(DoseFrequencyType type, AbstractDoseFrequencyDetail detail) {
        if (!type.supportsDetailClass(detail.getClass())) {
            throw new IllegalArgumentException("Type mismatch: " + type + " vs " + detail.getClass());
        }
        return new DoseFrequency(type, detail);
    }

    @JsonCreator
    public DoseFrequency(
            @JsonProperty("doseFrequencyType") DoseFrequencyType type,
            @JsonProperty("doseFrequencyDetail") AbstractDoseFrequencyDetail detail
    ) {
        this.doseFrequencyType = type;
        this.doseFrequencyDetail = detail;
    }
}
