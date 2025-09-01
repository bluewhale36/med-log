package com.bluewhale.medlog.med.application.usecase.medintakesnapshot;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateOrModifyNewMedSnapshotUseCase_Impl implements IF_UseCase<MedDTO, Void> {

    private final MedIntakeSnapshotService misServ;

    @Override
    public Void execute(MedDTO input) {
//        misServ.generateMedIntakeSnapshotByAppUserUuidForAfter14Days(input);
        return null;
    }
}
