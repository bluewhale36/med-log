package com.bluewhale.medlog.appuser.repository;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByAppUserUuid(AppUserUuid appUserUuid);

    Optional<Long> findIdByAppUserUuid(AppUserUuid appUserUuid);
}
