package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
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

    public static AppUserDTO from(AppUser entity) {
        return AppUserDTO.builder()
                .appUserUuid(entity.getAppUserUuid())
                .username(entity.getUsername())
                .password(null)
                .name(entity.getName())
                .email(entity.getEmail())
                .birthdate(entity.getBirthdate())
                .gender(entity.getGender())
                .enrolledAt(entity.getEnrolledAt())
                .appUserMetricLogDTOList(null)
                .build();
    }
}
