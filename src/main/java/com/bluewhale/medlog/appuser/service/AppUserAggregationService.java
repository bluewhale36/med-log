package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.entity.AppUserRole;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.dto.AppUserAggregationDTO;
import com.bluewhale.medlog.appuser.enums.Gender;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.security.enums.IsEnabled;
import com.bluewhale.medlog.security.enums.IsLocked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserAggregationService {

    private final AppUserRepository appUserRepo;

    public AppUserAggregationDTO getAppUserFullDTOByAppUserId(Long appUserId) {
        AppUser entity = appUserRepo.findById(appUserId).orElseThrow(
                () -> new IllegalArgumentException(String.format("App user id %s not found", appUserId))
        );
        return toAggregationDTO(entity);
    }

    private AppUserAggregationDTO toAggregationDTO(AppUser entity) {
        return AppUserAggregationDTO.builder()
                .appUserId(entity.getAppUserId())
                .appUserUuid(entity.getAppUserUuid())
                .username(entity.getUsername())
                .name(entity.getName())
                .email(entity.getEmail())
                .birthdate(entity.getBirthdate())
                .isEnabled(entity.getIsEnabled())
                .isLocked(entity.getIsLocked())
                .enrolledAt(entity.getEnrolledAt())
                .appUserRoleList(entity.getAppUserRole())
                .build();
    }
}
