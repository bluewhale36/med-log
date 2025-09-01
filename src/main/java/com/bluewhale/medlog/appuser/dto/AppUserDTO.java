package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AppUserDTO {

    private AppUserUuid appUserUuid;
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDate birthdate;
    private Gender gender;
    private LocalDateTime enrolledAt;
    private List<AppUserMetricLogDTO> appUserMetricLogDTOList;
}
