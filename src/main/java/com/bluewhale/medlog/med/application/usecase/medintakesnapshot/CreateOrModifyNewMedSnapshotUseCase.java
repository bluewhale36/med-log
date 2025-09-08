package com.bluewhale.medlog.med.application.usecase.medintakesnapshot;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateOrModifyNewMedSnapshotUseCase implements UseCase<AppUserUuid, Void> {

    private final MedIntakeSnapshotService misServ;

    @Override
    public Void execute(AppUserUuid input) {
        misServ.generateMedIntakeSnapshotByAppUserUuidForAfter14Days(input);
        return null;
    }
}
