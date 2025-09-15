package com.bluewhale.medlog.medintakerecord.dto;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RenderServiceRequestToken {

    private final AppUserUuid appUserUuid;
    private final LocalDate referenceDate;

    public static RenderServiceRequestToken of(
            AppUserUuid appUserUuid, @Nullable LocalDate referenceDate
    ) {
        if (referenceDate == null) {
            referenceDate = LocalDate.now();
        }
        return new RenderServiceRequestToken(appUserUuid, referenceDate);
    }
}
