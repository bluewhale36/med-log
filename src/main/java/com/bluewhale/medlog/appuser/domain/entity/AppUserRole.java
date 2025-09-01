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

    @Column(name = "app_user_id")
    private Long appUserId;

    @Enumerated(EnumType.STRING)
    private Role role;
}
