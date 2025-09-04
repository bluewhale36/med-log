package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.enums.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@ToString
public class AppUserRegisterDTO {

    private final String username;
    private final String password;

    private final String name;
    private final String email;
    private final LocalDate birthdate;
    private final Gender gender;

}
