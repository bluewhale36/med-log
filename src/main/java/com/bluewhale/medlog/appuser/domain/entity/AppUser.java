package com.bluewhale.medlog.appuser.domain.entity;

import com.bluewhale.medlog.appuser.dto.AppUserRegisterDTO;
import com.bluewhale.medlog.appuser.dto.AppUserUpdateDTO;
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
import java.util.UUID;

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
            cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "appUser"
    )
    private List<AppUserRole> appUserRole;

    public static AppUser create(AppUserRegisterDTO dto, String encodedPassword) {
        return AppUser.builder()
                .appUserId(null)
                .appUserUuid(new AppUserUuid(UUID.randomUUID().toString()))
                .username(dto.getUsername())
                .password(encodedPassword)
                .name(dto.getName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .isEnabled(IsEnabled.ENABLED)
                .isLocked(IsLocked.UNLOCKED)
                .enrolledAt(null)
                .appUserRole(null)
                .build();
    }

    public void update(AppUserUpdateDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.birthdate = dto.getBirthdate();
        this.gender = dto.getGender();
    }

}
