package com.bluewhale.medlog.appuser.application.service;

import com.bluewhale.medlog.appuser.application.usecase.RegisterNewRegularAppUserUseCase;
import com.bluewhale.medlog.appuser.dto.AppUserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserApplicationService {

    private final RegisterNewRegularAppUserUseCase registerNewRegularAppUserUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void registerNewAppUser(AppUserRegisterDTO appUserRegisterDTO) {
        registerNewRegularAppUserUseCase.execute(appUserRegisterDTO);
    }
}
