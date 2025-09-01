package com.bluewhale.medlog.security.service;

import com.bluewhale.medlog.security.domain.entity.UserDetails_Impl;
import com.bluewhale.medlog.security.repository.SecurityRepository;
import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService_Impl implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityRepository.findUsersByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );
        return new UserDetails_Impl(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getAppUserRole().stream().map(r -> r.getRole().toGrantedAuthority()).toList(),
                appUser.getAppUserUuid(),
                appUser.getIsEnabled(),
                appUser.getIsLocked()
        );
    }
}
