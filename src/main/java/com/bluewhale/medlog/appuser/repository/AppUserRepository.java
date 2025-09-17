package com.bluewhale.medlog.appuser.repository;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, IdentifierRoutableRepository<AppUser, Long, AppUserUuid> {

    Optional<AppUser> findByAppUserUuid(AppUserUuid appUserUuid);

    @Override
    @Query("SELECT a.appUserId FROM AppUser a WHERE a.appUserUuid = :uuid")
    Optional<Long> findIdByUuid(AppUserUuid uuid);

    @Override
    @Query("SELECT a.appUserUuid FROM AppUser a WHERE a.appUserId = :id")
    Optional<AppUserUuid> findUuidById(Long id);
}
