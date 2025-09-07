package com.bluewhale.medlog.med.application.usecase.medintakerecord;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.service.AppUserConvertService_Impl;
import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.service.MedAggregationService;
import com.bluewhale.medlog.medintakerecord.domain.policy.MedIntakeRecordViewItemDTORenderPolicy;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import com.bluewhale.medlog.medintakerecord.factory.MedIntakeRecordDayViewDTOFactory;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeSnapshot;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyResultToken;
import com.bluewhale.medlog.medintakesnapshot.dto.MedIntakeSnapshotAggregationDTO;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetMedIntakeRecordViewDTOListUseCase implements IF_UseCase<AppUserUuid, List<MedIntakeRecordDayViewDTO>> {

    private final MedAggregationService medAggServ;
    private final MedIntakeSnapshotAggregationService misAggServ;

    private final AppUserConvertService_Impl appUserCServ;

    private final MedIntakeRecordViewItemDTORenderPolicy viewItemDTORenderPolicy;

    private final MedIntakeRecordDayViewDTOFactory mirdvDTOFactory;

    @Override
    public List<MedIntakeRecordDayViewDTO> execute(AppUserUuid input) {
        /*
            AppUserUuid 가 등록한 모든 약의 Aggregation DTO List 가져온다.
         */
        List<MedAggregationDTO> medAggDTOList = medAggServ.getMedAggDTOListByAppUserId(appUserCServ.getIdByUuid(input)).stream()
                .filter(
                        dto -> !dto.getDoseFrequency().getDoseFrequencyType().equals(DoseFrequencyType.AS_NEEDED)
                )
                .toList();

        /*
            모든 약 정보에 대한 복용 기록 Aggregation DTO List 가져온다.
         */
        List<MedIntakeRecordAggregationDTO> mirAggDTOList = medAggDTOList.stream()
                .map(MedAggregationDTO::getMirAggrDTOList)
                .flatMap(List::stream)
                .toList();

        /*
            모든 약 정보에 대한 복용 일정 Snapshot Aggregation DTO List 가져온다.
         */
        List<MedIntakeSnapshotAggregationDTO> misAggDTOList = medAggDTOList.stream()
                .map(MedAggregationDTO::getMedId)
                .map(misAggServ::getMisAggDTOListByMedId)
                .flatMap(List::stream)
                .toList();

        /*
            판단 정책에 대한 Request Token 을 복용 기록을 기준으로 생성하여 List 로 저장한다.
         */
        List<RenderPolicyRequestTokenForMedIntakeRecord> reqTknForMirList =
                mirAggDTOList.stream()
                        .map(
                                dto -> new RenderPolicyRequestTokenForMedIntakeRecord(
                                        dto.getMedIntakeRecordId(), dto.getMedId(), dto.getEstimatedDoseTime()
                                )
                        )
                        .toList();

        /*
            판단 정책에 대한 Request Token 을 복용 일정을 기준으로 생성하여 List 로 저장한다.
         */
        List<RenderPolicyRequestTokenForMedIntakeSnapshot> reqTknForMisList =
                misAggDTOList.stream()
                        .map(
                                dto -> new RenderPolicyRequestTokenForMedIntakeSnapshot(
                                        dto.getMedIntakeSnapshotId(), dto.getMedId(), dto.getEstimatedDoseTime()
                                )
                        )
                        .toList();

        /*
            두 개 Token List 전달하고 판단을 수행한 결과로 반환된 Result Token List 를 기준 일자에 대해 그룹화 한다.
         */
        Map<LocalDate, List<RenderPolicyResultToken>> resTknListGroupedByStdDate =
                viewItemDTORenderPolicy.getRenderPolicyResultList(reqTknForMirList, reqTknForMisList).stream()
                        .collect(
                                Collectors.groupingBy(RenderPolicyResultToken::stdDate)
                        );

        /*
            주어진 Result Token List 를 DTO 로 변환한다.
         */
        List<MedIntakeRecordDayViewDTO> resultList = new ArrayList<>();
        List<RenderPolicyResultToken> tmpResTknList;
        // 기준 일자에 대해 반복문 수행.
        for (LocalDate stdDate : resTknListGroupedByStdDate.keySet()) {
            tmpResTknList = resTknListGroupedByStdDate.get(stdDate);

            List<MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO> tmpViewItemDTOList = new ArrayList<>();
            // 각 기준 일자에 대한 Result Token List 에 대해 반복문 수행.
            for (RenderPolicyResultToken tmpResTkn : tmpResTknList) {
                // 내부의 MedId 에 대해, 이전에 가져왔던 약 정보를 찾는다.
                MedAggregationDTO medAggDTO = medAggDTOList.stream()
                        .filter(dto -> dto.getMedId().equals(tmpResTkn.medId()))
                        .findFirst()
                        .orElseThrow(
                                () -> new IllegalStateException(String.format("MedId %s not found", tmpResTkn.medId()))
                        );
                // 현재 Result Token 의 RecordViewType 이 RECORD 일 경우.
                if (tmpResTkn.recordViewType().equals(RecordViewType.RECORD)) {
                    // 실제 복용 기록을 찾는다.
                    MedIntakeRecordAggregationDTO mirAggDTO = medAggDTO.getMirAggrDTOList().stream()
                            .filter(dto -> dto.getEstimatedDoseTime().isEqual(LocalDateTime.of(tmpResTkn.stdDate(), tmpResTkn.stdTime())))
                            .findFirst()
                            .orElseThrow(
                                    () -> new IllegalStateException(String.format("No Intake Record At %s", stdDate))
                            );
                    tmpViewItemDTOList.add(mirdvDTOFactory.createMirViewItemDTOAsRecordType(tmpResTkn, medAggDTO, mirAggDTO));
                } else {
                    tmpViewItemDTOList.add(mirdvDTOFactory.createMirViewItemDTOAsScheduledType(tmpResTkn, medAggDTO));
                }
            }
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeRecord =
                    tmpViewItemDTOList.stream()
                            .filter(dto -> dto.getRecordViewType().equals(RecordViewType.RECORD))
                            .sorted(Comparator.comparing(MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO::getStdDateTime))
                            .collect(
                                    Collectors.groupingBy(
                                            viewItem -> viewItem.getStdDateTime().toLocalTime()
                                    )
                            );
            Map<LocalTime, List<MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO>> viewItemDTOListMapForTypeScheduled =
                    tmpViewItemDTOList.stream()
                            .filter(dto -> dto.getRecordViewType().equals(RecordViewType.SCHEDULED))
                            .sorted(Comparator.comparing(MedIntakeRecordDayViewDTO.MedIntakeRecordViewItemDTO::getStdDateTime))
                            .collect(
                                    Collectors.groupingBy(
                                            viewItem -> viewItem.getStdDateTime().toLocalTime()
                                    )
                            );
            resultList.add(
                    mirdvDTOFactory.createMedIntakeRecordDayViewDTO(
                            stdDate,
                            new TreeMap<>(viewItemDTOListMapForTypeRecord),
                            new TreeMap<>(viewItemDTOListMapForTypeScheduled)
                    )
            );
        }
        return resultList.stream().sorted(Comparator.comparing(MedIntakeRecordDayViewDTO::getStdDate)).toList();
    }
}
