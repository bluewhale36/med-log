package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.service.AbstractIdentifierConvertService;
import com.bluewhale.medlog.common.service.IdentifierConvertService;
import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AppUserIdentifierConvertService
        extends AbstractIdentifierConvertService<AppUser, AppUserUuid> {

    private final AppUserRepository appUserRepository;


    @Override
    protected IdentifierRoutableRepository<AppUser, Long, AppUserUuid> getRepository() {
        return appUserRepository;
    }

    @Override
    protected Function<Long, Optional<AppUserUuid>> uuidFinder() {
        return appUserRepository::findUuidById;
    }

    @Override
    protected Function<AppUserUuid, Optional<Long>> idFinder() {
        return appUserRepository::findIdByUuid;
    }

    @Override
    protected String getEntityName() {
        return "AppUser";
    }
}
