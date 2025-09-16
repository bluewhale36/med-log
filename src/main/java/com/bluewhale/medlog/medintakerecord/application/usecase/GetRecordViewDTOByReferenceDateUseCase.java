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

@Component
@RequiredArgsConstructor
@Slf4j
public class GetRecordViewDTOByReferenceDateUseCase
        implements UseCase<RenderServiceRequestToken, Optional<MedIntakeRecordDayViewDTO>> {

    private final AppUserIdentifierConvertService appUserIdentifierConvertService;

    private final MedIntakeSnapshotRepository snapshotRepository;

    private final MedRepository medRepository;

    @Override
    public Optional<MedIntakeRecordDayViewDTO> execute(RenderServiceRequestToken input) {
        /*
            0. input : AppUserUuid(현재 로그인 사용자), ReferenceDate(기준 일자)
         */

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

        /*
            3. snapshotList 에서 medIdList 추출
         */
        List<Long> medIdList = snapshotList.stream()
                .map(s -> s.getMed().getMedId())
                .distinct()
                .toList();
        if (!medIdList.isEmpty()) {

            /*
                ViewDTO List 저장용 리스트
             */
            List<MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO> viewItemTypeRecordDTOList = new ArrayList<>();
            List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO> viewItemTypeScheduledDTOList = new ArrayList<>();

            /*
                4. medIdList 로 Med List 조회 및 Map 변환 -> 시간 복잡도 절감
             */
            Map<Long, Med> medMap = medRepository.findAllById(medIdList).stream()
                    .collect(Collectors.toMap(Med::getMedId, Function.identity()));
            for (Med med : medMap.values()) {

                if (med.getDoseFrequency().getDoseFrequencyType().equals(DoseFrequencyType.AS_NEEDED)) {
                    // Med 가 없거나, AS_NEEDED 인 경우는 건너뛴다.
                    continue;
                }

                // Intake Record 존재 유무를 기준으로 RECORD, SCHEDULED type DTO 생성 -> 미리 Map 변환하여 시간 복잡도 절감
                Map<LocalDateTime, MedIntakeRecord> recordMap = med.getMedIntakeRecordList().stream()
                        .collect(Collectors.toMap(MedIntakeRecord::getEstimatedDoseTime, Function.identity()));

                // AS_NEEDED 는 위에서 걸러졌으므로, doseTimeCountList 는 반드시 존재함
                for (DoseTimeCount doseTimeCount : med.getDoseFrequency().getDoseFrequencyDetail().getDoseTimeCountList()) {

                    // 기준 일자 + 투약 시간 -> 기준 일시
                    LocalDateTime referenceDateTime = LocalDateTime.of(input.getReferenceDate(), doseTimeCount.getDoseTime());

                    // 해당 일시의 Intake Record 조회
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
