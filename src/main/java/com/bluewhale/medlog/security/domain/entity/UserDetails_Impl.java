package com.bluewhale.medlog.security.domain.entity;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.security.enums.IsEnabled;
import com.bluewhale.medlog.security.enums.IsLocked;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetails_Impl implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private final AppUserUuid appUserUuid;
    private final IsEnabled isEnabled;
    private final IsLocked isLocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonLocked() && isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked.getIsLocked();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled.getIsEnabled();
    }
}
