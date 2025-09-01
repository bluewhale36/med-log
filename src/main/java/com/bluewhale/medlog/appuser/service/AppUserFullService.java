package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.dto.AppUserAggregationDTO;
import com.bluewhale.medlog.appuser.mapper.AppUserMapper;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserFullService {

    private final AppUserRepository appUserRepo;
    private final AppUserMapper appUserMapper;

    public AppUserAggregationDTO getAppUserFullDTOByAppUserId(Long appUserId) {
        AppUser entity = appUserRepo.findById(appUserId).orElseThrow(
                () -> new IllegalArgumentException(String.format("App user id %s not found", appUserId))
        );
        return appUserMapper.toFullDTO(entity);
    }
}
