package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * MedUuid 에 대하여, Med 데이터는 Soft Delete 하고, 그에 관련된 Snapshot 데이터는 Hard Delete 하는 UseCase.<br/>
 * <b>실질적으로 Med 가 삭제되지 않음.<b/>
 *
 * @see HardDeleteMedUseCase
 */
@Component
@RequiredArgsConstructor
public class SoftDeleteMedUseCase implements IF_UseCase<MedUuid, Void> {

    private final MedRepository medRepository;
    private final SnapshotPolicyRepository snapshotPolicyRepository;

    @Override
    public Void execute(MedUuid input) {
        Long medId = medRepository.findIdByMedUuid(input).orElseThrow(
                () -> new IllegalArgumentException(String.format("No Med Found with MedUuid %s", input))
        );
        snapshotPolicyRepository.deleteByMed_MedId(medId);
        medRepository.deleteById(medId);
        return null;
    }
}
