package com.bluewhale.medlog.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum IsEnabled {

    ENABLED(Boolean.TRUE),
    DISABLED(Boolean.FALSE);

    private final Boolean isEnabled;
}
