package com.bluewhale.medlog.appuser.domain.entity;

import com.bluewhale.medlog.security.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "app_user_role")
public class AppUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    @ToString.Exclude
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static AppUserRole create(AppUser appUser, Role role) {
        return AppUserRole.builder()
                .roleId(null)
                .appUser(appUser)
                .role(role)
                .build();
    }

}
