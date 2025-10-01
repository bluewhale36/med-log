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

        // TODO : 로직 구현 완성 필요.
        LocalDate referenceDate = input.getReferenceDate();
        LocalDate today = LocalDate.now();
        LocalDate fromDate, toDate;

        if (referenceDate.isAfter(today)) {
            toDate = today.plusDays(14);
        } else {
            toDate = referenceDate.plusDays(14);
        }
        fromDate = referenceDate.minusDays(14);

        List<LocalDate> dateRange = fromDate.datesUntil(toDate.plusDays(1)).toList();

        medIntakeRecordService.updateRecordDayViewCache(input.getAppUserUuid(), input.getReferenceDate());
        return null;
    }
}
