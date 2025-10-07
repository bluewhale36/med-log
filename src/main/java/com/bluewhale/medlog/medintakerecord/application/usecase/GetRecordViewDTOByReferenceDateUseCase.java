package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.dto.dto.MedViewProjection;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.model.RenderServiceRequestToken;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import com.bluewhale.medlog.medintakerecord.service.MedIntakeRecordService;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.repository.MedIntakeSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 특정 사용자의 특정 일자에 대한 복약 기록 및 예정 내역을 조회하는 UseCase.
 * <p>
 * - RECORD 타입: 해당 일자에 복약 기록이 존재하는 경우
 * - SCHEDULED 타입: 해당 일자에 복약 기록이 존재하지 않는 예정된 복약 내역
 * </p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetRecordViewDTOByReferenceDateUseCase
        implements UseCase<RenderServiceRequestToken, Optional<MedIntakeRecordDayViewDTO>> {

    private final MedIntakeRecordService medIntakeRecordService;

    @Override
    public Optional<MedIntakeRecordDayViewDTO> execute(RenderServiceRequestToken input) {
        return medIntakeRecordService.processRecordViewDTO(input);
    }
}
