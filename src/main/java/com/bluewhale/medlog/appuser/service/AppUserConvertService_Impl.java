package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.common.service.ConvertService;
import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserConvertService_Impl implements ConvertService<AppUser, AppUserUuid> {

    private final AppUserRepository appUserRepository;

    @Override
    public Long getIdByUuid(AppUserUuid appUserUuid) {
        return getEntityByUuid(appUserUuid).getAppUserId();
    }

    @Override
    public AppUser getEntityByUuid(AppUserUuid appUserUuid) {
        return appUserUuid != null ?
                appUserRepository.findByAppUserUuid(appUserUuid).orElseThrow(
                        () -> new IllegalArgumentException(String.format("User with uuid %s not found", appUserUuid))
                ) :
                null;
    }

    @Override
    public AppUserUuid getUuidById(Long id) {
        AppUser appUser = id != null ?
                appUserRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException(String.format("User with id %s not found", id))
                ) :
                null;
        return appUser != null ? appUser.getAppUserUuid() : null;
    }
}
