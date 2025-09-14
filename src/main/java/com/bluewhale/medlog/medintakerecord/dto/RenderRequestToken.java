package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class RenderRequestToken {

    private final AppUserUuid appUserUuid;
    private final LocalDate date;

    public RenderRequestToken(AppUserUuid appUserUuid, @Nullable LocalDate date) {
        this.appUserUuid = appUserUuid;
        this.date = date == null ? LocalDate.now() : date;
    }

}
