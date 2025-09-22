package com.bluewhale.medlog.med.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * MedUuid 에 대하여 Med 와 그에 관련된 Record, Snapshot 데이터를 Hard Delete 하는 UseCase.<br/>
 * <b>Med 와 관련 정보를 데이터베이스에서 물리적으로 삭제함.</b>
 */
@Component
@RequiredArgsConstructor
public class HardDeleteMedUseCase implements UseCase<MedUuid, Void> {

    private final MedRepository medRepository;
    private final MedIdentifierConvertService medIdentifierConvertService;

    @Override
    public Void execute(MedUuid input) {
        Long medId = medIdentifierConvertService.getIdByUuid(input);
        /*
            Cascade 옵션에 따라 Record 및 Snapshot 전체 삭제됨.
         */
        medRepository.hardDeleteByMedId(medId);
        return null;
    }
}
