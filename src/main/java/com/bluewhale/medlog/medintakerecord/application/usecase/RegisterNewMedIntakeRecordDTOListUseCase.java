package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotModifyIsTakenDTO;
import com.bluewhale.medlog.medintakesnapshot.repository.SnapshotPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterNewMedIntakeRecordDTOListUseCase implements UseCase<List<MedIntakeRecordRegisterDTO>, List<MedIntakeRecordDTO>> {

    private final MedIdentifierConvertService medIdentifierConvertService;

    private final MedRepository medRepository;

    private final MedIntakeRecordRepository medIntakeRecordRepository;

    private final SnapshotPolicyRepository snapshotPolicyRepository;


    @Override
    public List<MedIntakeRecordDTO> execute(List<MedIntakeRecordRegisterDTO> input) {

        /*
            복용 기록 INSERT
         */
        List<MedIntakeRecord> medIntakeRecordList = new ArrayList<>();

        for (MedIntakeRecordRegisterDTO dto : input) {
            Long medId = medIdentifierConvertService.getIdByUuid(dto.getMedUuid());
            Med medReference = medRepository.getReferenceById(medId);
            MedIntakeRecord entity = MedIntakeRecord.create(dto, medReference);
            medIntakeRecordList.add(entity);
        }
        medIntakeRecordList = medIntakeRecordRepository.saveAll(medIntakeRecordList);
        List<MedIntakeRecordDTO> medIntakeRecordDTOList = medIntakeRecordList.stream().map(MedIntakeRecordDTO::from).toList();

        /*
            Snapshot UPDATE
         */
        for (MedIntakeSnapshotModifyIsTakenDTO dto : medIntakeRecordDTOList.stream().map(MedIntakeSnapshotModifyIsTakenDTO::from).toList()) {
            Long medId = medIdentifierConvertService.getIdByUuid(dto.getMedUuid());

            MedIntakeSnapshot entity =
                    snapshotPolicyRepository.findByMedIdAndEstimatedDoseTime(medId, dto.getEstimatedDoseTime());

            if (entity != null) {
                entity.updateIsTaken(dto.isTaken());
            } else {
                throw new IllegalStateException("MedIntakeSnapshot not found with medId : " + medId + " And EstimatedDoseTime : " + dto.getEstimatedDoseTime());
            }
        }

        return medIntakeRecordDTOList;
    }
}
