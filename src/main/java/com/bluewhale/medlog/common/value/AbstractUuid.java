package com.bluewhale.medlog.common.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractUuid {

    private final String value;

    protected AbstractUuid(String value) {
        validate(value);
        this.value = value;
    }

    protected void validate(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID: " + uuid);
        }
    }
}
