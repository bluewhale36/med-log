package com.bluewhale.medlog.medintakesnapshot.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateNewMedSnapshotByMedUuidUseCase implements UseCase<MedUuid, Void> {

    private final MedIntakeSnapshotService medIntakeSnapshotService;

    @Override
    public Void execute(MedUuid input) {
        medIntakeSnapshotService.registerMedIntakeSnapshotByMedDTO(input);
        return null;
    }
}
