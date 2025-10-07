package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.enums.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

public record AppUserRegisterDTO(
        String username,
        String password,
        String name,
        String email,
        LocalDate birthdate,
        Gender gender
) {

}
