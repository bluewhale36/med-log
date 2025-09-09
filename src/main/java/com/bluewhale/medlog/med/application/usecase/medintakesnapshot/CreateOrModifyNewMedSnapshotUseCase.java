package com.bluewhale.medlog.med.application.usecase.medintakesnapshot;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateOrModifyNewMedSnapshotUseCase implements UseCase<AppUserUuid, Void> {

    private final MedIntakeSnapshotService medIntakeSnapshotService;
    private final SnapshotPolicyRepository snapshotPolicyRepository;
    private final AppUserIdentifierConvertService appUserIdentifierConvertService;

    @Override
    public Void execute(AppUserUuid input) {
        Long appUserId = appUserIdentifierConvertService.getIdByUuid(input);
        snapshotPolicyRepository.deleteByAppUser_AppUserId(appUserId);

        medIntakeSnapshotService.generateMedIntakeSnapshotByAppUserUuidForAfter14Days(input);
        return null;
    }
}
