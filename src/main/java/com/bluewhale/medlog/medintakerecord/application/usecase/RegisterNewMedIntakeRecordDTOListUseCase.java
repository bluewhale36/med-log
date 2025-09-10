package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
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

    @Override
    public List<MedIntakeRecordDTO> execute(List<MedIntakeRecordRegisterDTO> input) {

        List<MedIntakeRecord> entityList = new ArrayList<>();

        for (MedIntakeRecordRegisterDTO dto : input) {
            Long medId = medIdentifierConvertService.getIdByUuid(dto.getMedUuid());
            Med medReference = medRepository.getReferenceById(medId);
            MedIntakeRecord entity = MedIntakeRecord.create(dto, medReference);
            entityList.add(entity);
        }

        entityList = medIntakeRecordRepository.saveAll(entityList);

        return entityList.stream().map(MedIntakeRecordDTO::from).toList();
    }
}
