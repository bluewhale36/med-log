package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.dto.RenderServiceRequestToken;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
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

    private final AppUserIdentifierConvertService appUserIdentifierConvertService;

    private final MedIntakeSnapshotRepository snapshotRepository;

    private final MedRepository medRepository;

    private final MedIntakeRecordRepository recordRepository;

    @Override
    public Optional<MedIntakeRecordDayViewDTO> execute(RenderServiceRequestToken input) {
        /*
            0. input : AppUserUuid(현재 로그인 사용자), ReferenceDate(기준 일자)
         */
        log.info("Executing GetRecordViewDTOByReferenceDateUseCase for AppUserUuid: {}, ReferenceDate: {}",
                input.getAppUserUuid(), input.getReferenceDate());
        /*
            1. AppUserUuid 에 해당하는 AppUserId 조회
         */
        Long appUserId = appUserIdentifierConvertService.getIdByUuid(input.getAppUserUuid());
        log.info("Found AppUserId: {} for AppUserUuid: {}", appUserId, input.getAppUserUuid());

        /*
            2. AppUserId 와 ReferenceDate 로 snapshot 조회
                - EstimatedDoseTime 이 ReferenceDate 에 속한 snapshot 들
         */
        List<MedIntakeSnapshot> snapshotList =
                snapshotRepository.findAllByAppUser_AppUserIdAndEstimatedDoseTimeIsBetween(
                        appUserId,
                        input.getReferenceDate().atStartOfDay(),
                        input.getReferenceDate().plusDays(1).atStartOfDay()
                );
        log.info("Found {} snapshots for AppUserId: {} on ReferenceDate: {}",
                snapshotList.size(), appUserId, input.getReferenceDate());

        /*
            3. snapshotList 에서 medIdList 추출
         */
        List<Long> medIdList = snapshotList.stream()
                .map(s -> s.getMed().getMedId())
                .distinct()
                .toList();
        log.info("Extracted {} distinct MedIds from snapshots.", medIdList.size());
        if (!medIdList.isEmpty()) {

            /*
                ViewDTO List 저장용 리스트
             */
            List<MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO> viewItemTypeRecordDTOList = new ArrayList<>();
            List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO> viewItemTypeScheduledDTOList = new ArrayList<>();

            /*
                4. medIdList 로 Med List 조회 -> MedIntakeRecordList 도 함께 조회 (left fetch join)
                    - MedIntakeRecord 를 left fetch join 하므로, 일시에 대한 범위가 필요함.
             */
            List<Med> medList = medRepository.findAllById(medIdList);
            log.info("Found {} Med entities with MedIntakeRecords for the given MedIds.", medList.size());
            for (Med med : medList) {

                if (
                        med == null ||
                        med.getDoseFrequency().getDoseFrequencyType().equals(DoseFrequencyType.AS_NEEDED)
                ) {
                    // Med 가 없거나, AS_NEEDED 인 경우는 건너뛴다.
                    continue;
                }

                List<MedIntakeRecord> currRecordList =
                        recordRepository.findAllByMed_MedIdAndEstimatedDoseTimeIsBetween(
                                med.getMedId(),
                                input.getReferenceDate().atStartOfDay(),
                                input.getReferenceDate().plusDays(1).atStartOfDay()
                        );
                log.info("For MedId: {}, found {} MedIntakeRecords on ReferenceDate: {}",
                        med.getMedId(), currRecordList.size(), input.getReferenceDate());
                // Intake Record 존재 유무를 기준으로 RECORD, SCHEDULED type DTO 생성
                // -> 미리 Map 으로 변환하여 추후 시간 복잡도 절감
                Map<LocalDateTime, MedIntakeRecord> recordMap =
                        !currRecordList.isEmpty() ?
                        currRecordList.stream()
                                .collect(
                                        Collectors.toMap(MedIntakeRecord::getEstimatedDoseTime, Function.identity())
                                ) :
                        Collections.emptyMap();

                // AS_NEEDED 는 위에서 걸러졌으므로, doseTimeCountList 는 반드시 존재함
                for (DoseTimeCount doseTimeCount : med.getDoseFrequency().getDoseFrequencyDetail().getDoseTimeCountList()) {

                    // 기준 일자 + 투약 시간 -> 기준 일시
                    LocalDateTime referenceDateTime = LocalDateTime.of(input.getReferenceDate(), doseTimeCount.getDoseTime());

                    // 해당 일시의 Intake Record 조회 (Map 으로 미리 변환)
                    MedIntakeRecord record = recordMap.get(referenceDateTime);
                    if (record != null) {
                        // 기록이 있는 경우 -> RECORD 타입 DTO 생성
                        viewItemTypeRecordDTOList.add(
                                MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO.of(
                                        record,
                                        LocalDateTime.of(
                                                input.getReferenceDate(), doseTimeCount.getDoseTime()
                                        )
                                )
                        );
                    } else {
                        // 기록이 없는 경우 -> SCHEDULED 타입 DTO 생성
                        viewItemTypeScheduledDTOList.add(
                                MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO.of(
                                        med,
                                        doseTimeCount.getDoseCount(),
                                        LocalDateTime.of(
                                                input.getReferenceDate(), doseTimeCount.getDoseTime()
                                        )
                                )
                        );
                    }
                }
            }

            /*
                9. 각각의 DTO 리스트를 기준 시각 오름차순으로 정렬 및 그룹화
             */
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO>> viewItemTypeRecordDTOMap =
                    viewItemTypeRecordDTOList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            dto -> dto.getReferenceDateTime().toLocalTime(),
                                            TreeMap::new,
                                            Collectors.toList()
                                    )
                            );
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO>> viewItemTypeScheduledDTOMap =
                    viewItemTypeScheduledDTOList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            dto -> dto.getReferenceDateTime().toLocalTime(),
                                            TreeMap::new,
                                            Collectors.toList()
                                    )
                            );
            /*
                10. MedIntakeRecordDayViewDTO 생성
             */
            MedIntakeRecordDayViewDTO result = MedIntakeRecordDayViewDTO.of(
                    input.getReferenceDate(),
                    viewItemTypeRecordDTOMap,
                    viewItemTypeScheduledDTOMap
            );
            return Optional.of(result);
        } else {
            log.info("No Med found for AppUserUuid: {}", input.getAppUserUuid());
            return Optional.empty();
        }
    }
}
