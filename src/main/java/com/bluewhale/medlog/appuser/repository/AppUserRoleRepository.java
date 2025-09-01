package com.bluewhale.medlog.appuser.repository;

import com.bluewhale.medlog.appuser.domain.entity.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
}
