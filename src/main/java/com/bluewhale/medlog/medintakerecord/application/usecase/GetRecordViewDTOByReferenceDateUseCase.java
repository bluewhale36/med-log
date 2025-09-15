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
                ViewDTO List 생성용 임시 리스트
             */
            List<MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO> viewItemTypeRecordDTOList = new ArrayList<>();
            List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO> viewItemTypeScheduledDTOList = new ArrayList<>();

            /*
                4. medIdList 로 Med List 조회
                    - AS_NEEDED 는 일정이 없으므로 제외
             */
            Map<Long, Med> medMap = medRepository.findAllById(medIdList).stream()
                    .collect(Collectors.toMap(Med::getMedId, Function.identity()));
            for (MedIntakeSnapshot snapshot : snapshotList) {
                Med med = medMap.get(snapshot.getMed().getMedId());
                if (med == null || med.getDoseFrequency().getDoseFrequencyType().equals(DoseFrequencyType.AS_NEEDED)) {
                    continue;
                }
                // AS_NEEDED 는 위에서 걸러졌으므로, doseTimeCountList 는 반드시 존재함
                for (DoseTimeCount doseTimeCount : med.getDoseFrequency().getDoseFrequencyDetail().getDoseTimeCountList()) {
                    // 우선 기록을 먼저 찾자. 기록이 있으면 기록을, 없으면 예정된 스케줄을 보여줘야 한다.
                    Map<LocalDateTime, MedIntakeRecord> recordMap = med.getMedIntakeRecordList().stream()
                            .collect(Collectors.toMap(MedIntakeRecord::getEstimatedDoseTime, Function.identity()));
                    LocalDateTime referenceDateTime = LocalDateTime.of(input.getReferenceDate(), doseTimeCount.getDoseTime());
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
                            .sorted(
                                    Comparator.comparing(MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO::getReferenceDateTime)
                            )
                            .collect(
                                    Collectors.groupingBy(
                                            dto -> dto.getReferenceDateTime().toLocalTime(),
                                            TreeMap::new,
                                            Collectors.toList()
                                    )
                            );
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO>> viewItemTypeScheduledDTOMap =
                    viewItemTypeScheduledDTOList.stream()
                            .sorted(
                                    Comparator.comparing(MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO::getReferenceDateTime)
                            )
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
