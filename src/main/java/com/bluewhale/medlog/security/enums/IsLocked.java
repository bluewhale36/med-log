package com.bluewhale.medlog.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum IsLocked {

    LOCKED(Boolean.TRUE),
    UNLOCKED(Boolean.FALSE);

    private final Boolean isLocked;
}
