package com.bluewhale.medlog.security.repository;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUsersByUsername(String username);
}
