package com.bluewhale.medlog.appuser.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.dto.AppUserDTO;
import com.bluewhale.medlog.security.enums.IsEnabled;
import com.bluewhale.medlog.security.enums.IsLocked;
import com.bluewhale.medlog.security.enums.Role;
import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.entity.AppUserRole;
import com.bluewhale.medlog.appuser.dto.AppUserRegisterDTO;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.repository.AppUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final PasswordEncoder passwordEncoder;

    private final AppUserConvertService_Impl appUSerQServ;

    private final AppUserRepository appUserRepository;
    private final AppUserRoleRepository appUserRoleRepository;

    public void registerNewUser(AppUserRegisterDTO dto) {
        String uuid = UUID.randomUUID().toString();

        AppUser user = AppUser.builder()
                .appUserId(null)
                .appUserUuid(new AppUserUuid(uuid))
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .isEnabled(IsEnabled.ENABLED)
                .isLocked(IsLocked.UNLOCKED)
                .enrolledAt(null)
                .build();

        AppUser insertedUser = appUserRepository.save(user);
        System.out.println(insertedUser);

        AppUserRole appUserRole = AppUserRole.builder()
                .roleId(null)
                .appUser(insertedUser)
                .role(Role.REGULAR)
                .build();
        AppUserRole insertedAppUserRole = appUserRoleRepository.save(appUserRole);
        System.out.println(insertedAppUserRole);
    }

    public AppUserDTO getAppUserDTOByAppUserUuid(AppUserUuid appUserUuid) {
        AppUser entity = appUserRepository.findByAppUserUuid(appUserUuid).orElseThrow(
                () -> new IllegalArgumentException(String.format("AppUserUuid %s not found", appUserUuid))
        );
        return AppUserDTO.from(entity);
    }


}
