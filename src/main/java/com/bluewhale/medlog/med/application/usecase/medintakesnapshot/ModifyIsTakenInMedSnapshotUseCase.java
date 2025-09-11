package com.bluewhale.medlog.med.application.usecase.medintakesnapshot;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotModifyIsTakenDTO;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModifyIsTakenInMedSnapshotUseCase
        implements UseCase<List<MedIntakeSnapshotModifyIsTakenDTO>, Void> {

    private final MedIdentifierConvertService medIdentifierConvertService;

    private final SnapshotPolicyRepository snapshotPolicyRepository;

    @Override
    public Void execute(List<MedIntakeSnapshotModifyIsTakenDTO> input) {

        for (MedIntakeSnapshotModifyIsTakenDTO dto : input) {
            Long medId = medIdentifierConvertService.getIdByUuid(dto.getMedUuid());

            MedIntakeSnapshot entity =
                    snapshotPolicyRepository.findByMedIdAndEstimatedDoseTime(medId, dto.getEstimatedDoseTime());

            if (entity != null) {
                entity.updateIsTaken(dto.isTaken());
            } else {
                throw new IllegalStateException("MedIntakeSnapshot not found with medId : " + medId + " And EstimatedDoseTime : " + dto.getEstimatedDoseTime());
            }
        }
        return null;
    }
}
