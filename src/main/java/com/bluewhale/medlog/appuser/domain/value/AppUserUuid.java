package com.bluewhale.medlog.appuser.domain.value;

import com.bluewhale.medlog.common.value.AbstractUuid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class AppUserUuid extends AbstractUuid {
    @JsonCreator
    public AppUserUuid(String uuid) {
        super(uuid);
    }

    @JsonProperty
    public String asString() {
        return getValue();
    }
}
