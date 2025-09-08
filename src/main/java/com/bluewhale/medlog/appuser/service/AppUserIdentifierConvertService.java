package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserIdentifierConvertService implements IdentifierConvertService<AppUserUuid> {

    private final AppUserRepository appUserRepository;

    @Override
    public Long getIdByUuid(AppUserUuid uuid) {
        if (uuid == null) {
            throw new NullIdentifierException("Uuid Value for AppUser is Null.");
        }

        return appUserRepository.findIdByAppUserUuid(uuid).orElseThrow(
                () -> new IllegalIdentifierException("AppUser with Uuid "+ uuid + " has not been found.")
        );
    }

    @Override
    public AppUserUuid getUuidById(Long id) {
        if (id == null) {
            throw new NullIdentifierException("Id Value for AppUser is Null.");
        }

        AppUser entityReference = appUserRepository.getReferenceById(id);

        AppUserUuid appUserUuid;
        try {
            appUserUuid = entityReference.getAppUserUuid();
        } catch (EntityNotFoundException e) {
            throw new IllegalIdentifierException("AppUser with id " + id + " has not been found.");
        }

        return appUserUuid;
    }
}
