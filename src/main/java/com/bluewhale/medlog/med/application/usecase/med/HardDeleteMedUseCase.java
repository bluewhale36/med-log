package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * MedUuid 에 대하여 Med 와 그에 관련된 Record, Snapshot 데이터를 Hard Delete 하는 UseCase.<br/>
 * <b>Med 와 관련 정보를 데이터베이스에서 물리적으로 삭제함.</b>
 */
@Component
@RequiredArgsConstructor
public class HardDeleteMedUseCase implements IF_UseCase<MedUuid, Void> {

    private final MedRepository medRepository;

    @Override
    public Void execute(MedUuid input) {
        Long medId = medRepository.findIdByMedUuid(input).orElseThrow(
                () -> new IllegalArgumentException(String.format("Med Not Found with UUID: %s", input))
        );
        /*
            Cascade 옵션에 따라 Record 및 Snapshot 전체 삭제됨.
         */
        medRepository.hardDeleteByMedId(medId);
        return null;
    }
}
