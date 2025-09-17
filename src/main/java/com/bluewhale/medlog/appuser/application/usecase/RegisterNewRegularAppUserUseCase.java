package com.bluewhale.medlog.appuser.application.usecase;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.dto.AppUserRegisterDTO;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterNewRegularAppUserUseCase implements UseCase<AppUserRegisterDTO, AppUser> {

    private final PasswordEncoder passwordEncoder;

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser execute(AppUserRegisterDTO input) {
        String encodedPassword = passwordEncoder.encode(input.getPassword());
        AppUser entity = AppUser.create(input, encodedPassword);
        entity.addRole(Role.REGULAR);

        /*
            AppUser Entity 객체 내 appUserRole 이 Cascade.ALL 이므로 권한까지 한번에 DB 에 저장된다.
         */
        return appUserRepository.save(entity);
    }
}
