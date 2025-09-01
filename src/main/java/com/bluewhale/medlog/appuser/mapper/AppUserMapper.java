package com.bluewhale.medlog.appuser.mapper;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.dto.AppUserDTO;
import com.bluewhale.medlog.appuser.dto.AppUserAggregationDTO;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUserDTO toDTO(AppUser entity) {
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

    public AppUserAggregationDTO toFullDTO(AppUser entity) {
        return AppUserAggregationDTO.builder()
                .appUserId(entity.getAppUserId())
                .appUserUuid(entity.getAppUserUuid())
                .username(entity.getUsername())
                .name(entity.getName())
                .email(entity.getEmail())
                .birthdate(entity.getBirthdate())
                .gender(entity.getGender())
                .isEnabled(entity.getIsEnabled())
                .isLocked(entity.getIsLocked())
                .enrolledAt(entity.getEnrolledAt())
                .appUserRoleList(entity.getAppUserRole())
                .build();
    }
}
