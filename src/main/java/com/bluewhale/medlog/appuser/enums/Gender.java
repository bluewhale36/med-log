package com.bluewhale.medlog.appuser.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    M("MALE"), F("FEMALE"), O("OTHER");

    private final String gender;

}
