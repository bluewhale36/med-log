package com.bluewhale.medlog.medintakesnapshot.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class MedIntakeSnapshotUuid extends AbstractUuid {
    
    @JsonCreator
    public MedIntakeSnapshotUuid(String value) {
        super(value);
    }

    @JsonProperty
    public String asString() {
        return getValue();
    }
}
