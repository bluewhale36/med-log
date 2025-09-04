package com.bluewhale.medlog.appuser.domain.entity;

import com.bluewhale.medlog.security.converter.IsEnabledConverter;
import com.bluewhale.medlog.security.converter.IsLockedConverter;
import com.bluewhale.medlog.security.enums.IsEnabled;
import com.bluewhale.medlog.security.enums.IsLocked;
import com.bluewhale.medlog.appuser.domain.persistence.AppUserUuidConverter;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name="app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @Column(name = "app_user_uuid")
    @Convert(converter = AppUserUuidConverter.class)
    private AppUserUuid appUserUuid;

    private String username;

    private String password;

    private String name;

    private String email;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Convert(converter = IsEnabledConverter.class)
    private IsEnabled isEnabled;

    @Convert(converter = IsLockedConverter.class)
    private IsLocked isLocked;

    private LocalDateTime enrolledAt;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE, orphanRemoval = true,
            mappedBy = "app_user"
    )
    @JoinColumn(name = "app_user_id")
    private List<AppUserRole> appUserRole;

}
