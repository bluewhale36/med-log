package com.bluewhale.medlog.hospital.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
public class VisitUuid extends AbstractUuid {
    @JsonCreator
    public VisitUuid(@JsonProperty("value") String visitUuid) {
        super(visitUuid);
    }

    public String asString() {
        return getValue();
    }
}
