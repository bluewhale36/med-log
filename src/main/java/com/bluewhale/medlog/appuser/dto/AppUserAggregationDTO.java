package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.domain.entity.AppUserRole;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.enums.Gender;
import com.bluewhale.medlog.security.enums.IsEnabled;
import com.bluewhale.medlog.security.enums.IsLocked;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
@ToString(exclude = "password")
@EqualsAndHashCode
public class AppUserAggregationDTO {

    private final Long appUserId;
    private final AppUserUuid appUserUuid;
    private final String username;
    private final String password = null;
    private final String name;
    private final String email;
    private final LocalDate birthdate;
    private final Gender gender;
    private final IsEnabled isEnabled;
    private final IsLocked isLocked;
    private final LocalDateTime enrolledAt;
    private final List<AppUserRole> appUserRoleList;

}
