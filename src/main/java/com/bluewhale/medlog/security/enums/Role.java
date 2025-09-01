package com.bluewhale.medlog.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
@Getter
@ToString
public enum Role {

    ADMIN("ROLE_ADMIN"),
    REGULAR("ROLE_REGULAR");

    private final String role;

    public GrantedAuthority toGrantedAuthority() {
        return new SimpleGrantedAuthority(this.role);
    }
}
