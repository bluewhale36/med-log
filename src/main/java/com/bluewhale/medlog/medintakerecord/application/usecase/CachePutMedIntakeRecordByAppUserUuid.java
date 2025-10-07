package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakerecord.model.RenderServiceRequestToken;
import com.bluewhale.medlog.medintakerecord.service.MedIntakeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CachePutMedIntakeRecordByAppUserUuid implements UseCase<RenderServiceRequestToken, Void> {

    private final MedIntakeRecordService medIntakeRecordService;

    @Override
    public Void execute(RenderServiceRequestToken input) {

        LocalDate referenceDate = input.getReferenceDate();
        LocalDate today = LocalDate.now();
        LocalDate fromDate, toDate;

        /*
            캐시 생성 일자 범위 지정.
            1. 최대 일자는 오늘로부터 14일 이후까지.
            2. 최소 일자는 기준 일자에서 14일 전까지.
            3. 캐시에 저장되는 일자의 수는 최대 4주.
         */
        if (referenceDate.isAfter(today)) {
            toDate = today.plusDays(14);
        } else {
            toDate = referenceDate.plusDays(14);
        }
        fromDate = referenceDate.minusDays(14);

        // fromDate 부터 toDate 까지의 일자를 LocalDate 로 담은 List.
        List<LocalDate> dateRange = fromDate.datesUntil(toDate.plusDays(1)).toList();

        // dateRange 의 각 일자에 대해 Redis Cache 대입.
        for (LocalDate date : dateRange) {
            medIntakeRecordService.updateRecordDayViewCache(input.getAppUserUuid(), date);
        }

        return null;
    }
}
