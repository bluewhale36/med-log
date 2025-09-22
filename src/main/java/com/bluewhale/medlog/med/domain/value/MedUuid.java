package com.bluewhale.medlog.med.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
public class MedUuid extends AbstractUuid {
    @JsonCreator
    public MedUuid(@JsonProperty("value") String medUuid) {
        super(medUuid);
    }

    public String asString() {
        return getValue();
    }
}
