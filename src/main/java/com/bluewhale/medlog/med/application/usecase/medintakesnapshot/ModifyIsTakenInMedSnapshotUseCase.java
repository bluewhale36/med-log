package com.bluewhale.medlog.med.application.usecase.medintakesnapshot;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotModifyIsTakenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModifyIsTakenInMedSnapshotUseCase
        implements UseCase<List<MedIntakeSnapshotModifyIsTakenDTO>, Void> {
    @Override
    public Void execute(List<MedIntakeSnapshotModifyIsTakenDTO> input) {
        return null;
    }
}
