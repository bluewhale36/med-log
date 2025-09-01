package com.bluewhale.medlog.hospital.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitUuid extends AbstractUuid {
    @JsonCreator
    public VisitUuid(String value) {
        super(value);
    }

    @JsonProperty
    public String asString() {
        return getValue();
    }
}
