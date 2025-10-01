package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.med.dto.dto.MedViewProjection;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.model.RenderServiceRequestToken;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import com.bluewhale.medlog.medintakesnapshot.repository.MedIntakeSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedIntakeRecordService {

    private final MedIntakeRecordRepository medIntakeRecordRepository;

    private final AppUserIdentifierConvertService appUserIdentifierConvertService;

    private final MedIntakeSnapshotRepository snapshotRepository;

    private final MedRepository medRepository;

    private final MedIntakeRecordRepository recordRepository;

    // TODO : 생성 로직 리팩토링 (notion 문서 참고)
    public Optional<MedIntakeRecordDayViewDTO> processRecordViewDTO(RenderServiceRequestToken requestToken) {
        /*
            0. requestToken : AppUserUuid(현재 로그인 사용자), ReferenceDate(기준 일자)
         */
        log.info("==============================================================================================");
        log.info("Executing GetRecordViewDTOByReferenceDateUseCase for AppUserUuid: {}, ReferenceDate: {}",
                requestToken.getAppUserUuid(), requestToken.getReferenceDate());
        /*
            1. AppUserUuid 에 해당하는 AppUserId 조회
         */
        Long appUserId = appUserIdentifierConvertService.getIdByUuid(requestToken.getAppUserUuid());
        log.info("Found AppUserId: {} for AppUserUuid: {}", appUserId, requestToken.getAppUserUuid());

        /*
            2. AppUserId 와 ReferenceDate 로 snapshot 조회
                - EstimatedDoseTime 이 ReferenceDate 에 속한 snapshot 들
         */
        log.warn("Finding snapshots... for AppUserId: {} between {} and {}",
                appUserId,
                requestToken.getReferenceDate().atStartOfDay(),
                requestToken.getReferenceDate().plusDays(1).atStartOfDay()
        );
        List<MedIntakeSnapshot> snapshotList =
                snapshotRepository.findAllByAppUser_AppUserIdAndEstimatedDoseTimeIsBetween(
                        appUserId,
                        requestToken.getReferenceDate().atStartOfDay(),
                        requestToken.getReferenceDate().plusDays(1).atStartOfDay()
                );
        log.info("Found {} snapshots for AppUserId: {} on ReferenceDate: {}",
                snapshotList.size(), appUserId, requestToken.getReferenceDate());

        /*
            3. snapshotList 에서 medIdList 추출
         */
        List<Long> medIdList = snapshotList.stream()
                .map(s -> s.getMed().getMedId())
                .distinct()
                .toList();
        log.info("Extracted {} distinct MedIds from snapshots. : {}", medIdList.size(), medIdList);
        if (!medIdList.isEmpty()) {

            /*
                ViewDTO List 저장용 리스트
             */
            List<MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO> viewItemTypeRecordDTOList = new ArrayList<>();
            List<MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO> viewItemTypeScheduledDTOList = new ArrayList<>();

            /*
                4. medIdList 로 Med List 조회
             */
            log.warn("Finding Med projections... for MedIds: {}", medIdList);
            List<MedViewProjection> medProjectionList = medRepository.findAllProjectionByMedIdIn(medIdList);
            log.info("Found {} Med projections with given MedIds.", medProjectionList.size());

            /*
                5. medIdList 와 기준 일자에 해당되는 MedIntakeRecord List 조회
             */
            log.warn("Finding MedIntakeRecords... for MedIds: {} between {} and {}",
                    medIdList,
                    requestToken.getReferenceDate().atStartOfDay(),
                    requestToken.getReferenceDate().plusDays(1).atStartOfDay()
            );
            List<MedIntakeRecord> recordList = recordRepository.findAllByMed_MedIdAndEstimatedDoseTimeInRange(
                    medIdList,
                    requestToken.getReferenceDate().atStartOfDay(),
                    requestToken.getReferenceDate().plusDays(1).atStartOfDay()
            );
            log.info("Found {} records for medIds: {}", recordList.size(), medIdList);

            /*
                6. MedId 별로 MedIntakeRecord List 를 맵으로 변환
             */
            Map<Long, List<MedIntakeRecord>> recordMapByMedId = recordList.stream()
                    .collect(
                            Collectors.groupingBy(
                                    r -> r.getMed().getMedId()
                            )
                    );
            for (MedViewProjection mewProjection : medProjectionList) {

                if (
                        mewProjection == null ||
                                mewProjection.getDoseFrequency().getDoseFrequencyType().equals(DoseFrequencyType.AS_NEEDED)
                ) {
                    // Med 가 없거나, AS_NEEDED 인 경우는 건너뛴다.
                    continue;
                }

                // 해당 약에 대한 MedIntakeRecord List 조회 (Map 으로 미리 변환)
                List<MedIntakeRecord> currRecordList =
                        recordMapByMedId.getOrDefault(mewProjection.getMedId(), Collections.emptyList());
                log.info("Found {} records for medId: {}", currRecordList.size(), mewProjection.getMedId());

                /*
                    7. 현재 약에 해당하는 MedIntakeRecord List 를 EstimatedDoseTime 기준으로 맵으로 변환
                 */
                Map<LocalDateTime, MedIntakeRecord> recordMapByEstimatedDoseTime = currRecordList.stream()
                        .collect(
                                Collectors.toMap(
                                        MedIntakeRecord::getEstimatedDoseTime,
                                        Function.identity()
                                )
                        );

                // AS_NEEDED 는 위에서 걸러졌으므로, doseTimeCountList 는 반드시 존재함
                for (DoseTimeCount doseTimeCount : mewProjection.getDoseFrequency().getDoseFrequencyDetail().getDoseTimeCountList()) {

                    // 기준 일자 + 투약 시간 -> 기준 일시
                    LocalDateTime referenceDateTime = LocalDateTime.of(requestToken.getReferenceDate(), doseTimeCount.getDoseTime());

                    // 해당 일시의 Intake Record 조회 (Map 으로 미리 변환)
                    MedIntakeRecord record = recordMapByEstimatedDoseTime.getOrDefault(referenceDateTime, null);
                    if (record != null) {
                        // 기록이 있는 경우 -> RECORD 타입 DTO 생성
                        viewItemTypeRecordDTOList.add(
                                MedIntakeRecordDayViewDTO.ViewItemTypeRecordDTO.of(
                                        record,
                                        LocalDateTime.of(
                                                requestToken.getReferenceDate(), doseTimeCount.getDoseTime()
                                        )
                                )
                        );
                        log.info(
                                "RECORD type DTO created for MedId: {}, ReferenceDateTime: {}",
                                mewProjection.getMedId(), referenceDateTime
                        );
                    } else {
                        // 기록이 없는 경우 -> SCHEDULED 타입 DTO 생성
                        viewItemTypeScheduledDTOList.add(
                                MedIntakeRecordDayViewDTO.ViewItemTypeScheduledDTO.of(
                                        mewProjection,
                                        doseTimeCount.getDoseCount(),
                                        LocalDateTime.of(
                                                requestToken.getReferenceDate(), doseTimeCount.getDoseTime()
                                        )
                                )
                        );
                        log.info(
                                "SCHEDULED type DTO created for MedId: {}, ReferenceDateTime: {}",
                                mewProjection.getMedId(), referenceDateTime
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
                    requestToken.getReferenceDate(),
                    viewItemTypeRecordDTOMap,
                    viewItemTypeScheduledDTOMap
            );
            return Optional.of(result);
        } else {
            log.info("No Med found for AppUserUuid: {}", requestToken.getAppUserUuid());
            return Optional.empty();
        }
    }


    // 캐시 업데이트 로직
    @CachePut(key = "#appUserUuid.asString().concat(':').concat(#referenceDate.toString())", value = "recordDayViewDTO")
    public MedIntakeRecordDayViewDTO updateRecordDayViewCache(AppUserUuid appUserUuid, LocalDate referenceDate) {

        return null;
    }
}
