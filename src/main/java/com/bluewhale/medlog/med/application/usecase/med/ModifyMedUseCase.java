package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.dto.MedDTO;
import com.bluewhale.medlog.med.dto.MedModifyDTO;
import com.bluewhale.medlog.med.parser.MedModifyPayloadParser;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.med.service.MedIdentifierConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ModifyMedUseCase implements UseCase<Map<String, Object>, MedDTO> {

    private final MedModifyPayloadParser medModifyPayloadParser;
    private final MedIdentifierConvertService medIdentifierConvertService;
    private final MedRepository medRepository;

    @Override
    public MedDTO execute(Map<String, Object> input) {
        MedModifyDTO modifyDTO = medModifyPayloadParser.parseData(input);

        // MedUuid 로 MedId 조회.
        Long medId = medIdentifierConvertService.getIdByUuid(modifyDTO.getMedUuid());

        // Entity 조회.
        Med med = medRepository.findById(medId).orElseThrow(
                () -> new IllegalIdentifierException("Med with id " + medId + " has not been found.")
        );

        /*
            Entity 상태 업데이트
            -> save() 하지 않아도 MedAppService 의 @Transactional 에 의해 자동 반영.
         */
        med.updateSchedule(modifyDTO);

        // Update 된 상태의 MedDTO 반환.
        return MedDTO.from(med);
    }
}
