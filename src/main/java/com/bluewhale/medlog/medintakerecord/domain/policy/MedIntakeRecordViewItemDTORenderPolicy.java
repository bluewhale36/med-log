package com.bluewhale.medlog.medintakerecord.domain.policy;

import com.bluewhale.medlog.medintakerecord.enums.RecordViewType;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeSnapshot;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyResultToken;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MedIntakeRecordViewItemDTORenderPolicy {

    public List<RenderPolicyResultToken> getRenderPolicyResultList(
           List<RenderPolicyRequestTokenForMedIntakeRecord> reqTokenForMedIntakeRecordList,
           List<RenderPolicyRequestTokenForMedIntakeSnapshot> reqTokenForMedIntakeSnapshotList
    ) {

        List<RenderPolicyResultToken> resultList = new ArrayList<>();

        Map<LocalDate, List<RenderPolicyRequestTokenForMedIntakeRecord>> reqTknForMirListMap =
                reqTokenForMedIntakeRecordList.stream().collect(
                        Collectors.groupingBy(RenderPolicyRequestTokenForMedIntakeRecord::getEstimatedDoseTimeAsLocalDate)
                );
        Map<LocalDate, List<RenderPolicyRequestTokenForMedIntakeSnapshot>> reqTknForMisListMap =
                reqTokenForMedIntakeSnapshotList.stream().collect(
                        Collectors.groupingBy(RenderPolicyRequestTokenForMedIntakeSnapshot::getEstimatedDoseTimeAsLocalDate)
                );

        List<LocalDate> dateList = new ArrayList<>();
        dateList.addAll(reqTknForMirListMap.keySet());
        dateList.addAll(reqTknForMisListMap.keySet());
        dateList = dateList.stream().distinct().sorted().toList();

        for (LocalDate stdDate : dateList) {
            List<RenderPolicyRequestTokenForMedIntakeRecord> tmpReqTknForMirList = reqTknForMirListMap.get(stdDate);
            List<RenderPolicyRequestTokenForMedIntakeSnapshot> tmpReqTknForMisList = reqTknForMisListMap.get(stdDate);

            resultList.addAll(judgeAsStdDate(stdDate, tmpReqTknForMirList, tmpReqTknForMisList));
        }

        return resultList.stream()
                .distinct()
                .sorted(Comparator
                        .comparing(RenderPolicyResultToken::stdDate)
                        .thenComparing(RenderPolicyResultToken::stdTime)
                )
                .toList();
    }

    private List<RenderPolicyResultToken> judgeAsStdDate(
            LocalDate stdDate,
            List<RenderPolicyRequestTokenForMedIntakeRecord> reqTknForMirList,
            List<RenderPolicyRequestTokenForMedIntakeSnapshot> reqTknForMisList
    ) {

        List<RenderPolicyResultToken> resultList = new ArrayList<>();

        if (reqTknForMirList == null || reqTknForMirList.isEmpty()) {
            resultList.addAll(getResultTokenListBySnapshotReqTknList(reqTknForMisList));
        } else if (reqTknForMisList == null || reqTknForMisList.isEmpty()) {
            resultList.addAll(getResultTokenListByRecordReqTknList(reqTknForMirList));
        } else {
            Map<LocalTime, List<RenderPolicyRequestTokenForMedIntakeRecord>> reqTknForMirListMap =
                    reqTknForMirList.stream().collect(
                            Collectors.groupingBy(RenderPolicyRequestTokenForMedIntakeRecord::getEstimatedDoseTimeAsLocalTime)
                    );
            Map<LocalTime, List<RenderPolicyRequestTokenForMedIntakeSnapshot>> reqTknForMisListMap =
                    reqTknForMisList.stream().collect(
                            Collectors.groupingBy(RenderPolicyRequestTokenForMedIntakeSnapshot::getEstimatedDoseTimeAsLocalTime)
                    );

            List<LocalTime> timeList = new ArrayList<>();
            timeList.addAll(reqTknForMirListMap.keySet());
            timeList.addAll(reqTknForMisListMap.keySet());
            timeList = timeList.stream().distinct().sorted().toList();

            List<RenderPolicyResultToken> tmpResultList;
            for (LocalTime stdTime : timeList) {
                List<RenderPolicyRequestTokenForMedIntakeRecord> tmpReqTknForMirList = reqTknForMirListMap.get(stdTime);
                List<RenderPolicyRequestTokenForMedIntakeSnapshot> tmpReqTknForMisList = reqTknForMisListMap.get(stdTime);

                tmpResultList = judgeAsStdTime(stdTime, tmpReqTknForMirList, tmpReqTknForMisList);
                resultList.addAll(
                        tmpResultList.stream()
                                .map(
                                        r -> new RenderPolicyResultToken(r.medId(), stdDate, r.stdTime(), r.recordViewType())
                                )
                                .toList()
                );
            }
        }
        return resultList;
    }


    private List<RenderPolicyResultToken> judgeAsStdTime(
            LocalTime stdTime,
            List<RenderPolicyRequestTokenForMedIntakeRecord> reqTknForMirList,
            List<RenderPolicyRequestTokenForMedIntakeSnapshot> reqTknForMisList
    ) {
        List<RenderPolicyResultToken> resultList = new ArrayList<>();

        if (reqTknForMirList == null || reqTknForMirList.isEmpty()) {
            resultList.addAll(getResultTokenListBySnapshotReqTknList(reqTknForMisList));
        } else if (reqTknForMisList == null || reqTknForMisList.isEmpty()) {
            resultList.addAll(getResultTokenListByRecordReqTknList(reqTknForMirList));
        } else {
            List<Long> medIdList = new ArrayList<>();
            medIdList.addAll(reqTknForMirList.stream().map(RenderPolicyRequestTokenForMedIntakeRecord::medId).toList());
            medIdList.addAll(reqTknForMisList.stream().map(RenderPolicyRequestTokenForMedIntakeSnapshot::medId).toList());
            medIdList = medIdList.stream().distinct().toList();

            for (Long medId : medIdList) {
                RenderPolicyResultToken resultToken;

                RenderPolicyRequestTokenForMedIntakeRecord reqTknForMir =reqTknForMirList.stream()
                        .filter(r -> r.medId().equals(medId))
                        .findFirst()
                        .orElse(null);
                RenderPolicyRequestTokenForMedIntakeSnapshot reqTknForMis = reqTknForMisList.stream()
                        .filter(r -> r.medId().equals(medId))
                        .findFirst()
                        .orElse(null);

                if (reqTknForMir == null) {
                    resultToken = new RenderPolicyResultToken(medId, null, stdTime, RecordViewType.SCHEDULED);
                } else if (reqTknForMis == null) {
                    resultToken = new RenderPolicyResultToken(medId, null, stdTime, RecordViewType.RECORD);
                } else {
                    resultToken = new RenderPolicyResultToken(medId, null, stdTime, RecordViewType.RECORD);
                }
                resultList.add(resultToken);
            }
        }
        return resultList;
    }

    private List<RenderPolicyResultToken> getResultTokenListBySnapshotReqTknList(
            List<RenderPolicyRequestTokenForMedIntakeSnapshot> reqTknForMisList
    ) {
        return reqTknForMisList.stream()
                .map(
                        r -> new RenderPolicyResultToken(
                                r.medId(),
                                r.getEstimatedDoseTimeAsLocalDate(),
                                r.getEstimatedDoseTimeAsLocalTime(),
                                RecordViewType.SCHEDULED
                        )
                )
                .toList();
    }

    private List<RenderPolicyResultToken> getResultTokenListByRecordReqTknList(
            List<RenderPolicyRequestTokenForMedIntakeRecord> reqTknForMirList
    ) {
        return reqTknForMirList.stream()
                .map(
                        r -> new RenderPolicyResultToken(
                                r.medId(),
                                r.getEstimatedDoseTimeAsLocalDate(),
                                r.getEstimatedDoseTimeAsLocalTime(),
                                RecordViewType.RECORD
                        )
                )
                .toList();
    }
}
