package com.bluewhale.medlog.medintakerecord.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class MedIntakeRecordUuid extends AbstractUuid {
    @JsonCreator
    public MedIntakeRecordUuid(String value) {
        super(value);
    }

    @JsonProperty
    public String asString() {
        return getValue();
    }
}
