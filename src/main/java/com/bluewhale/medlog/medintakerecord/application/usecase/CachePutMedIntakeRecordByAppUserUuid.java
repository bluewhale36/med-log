package com.bluewhale.medlog.medintakerecord.application.usecase;

import com.bluewhale.medlog.common.application.usecase.UseCase;
import com.bluewhale.medlog.medintakerecord.model.RenderServiceRequestToken;
import com.bluewhale.medlog.medintakerecord.service.MedIntakeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CachePutMedIntakeRecordByAppUserUuid implements UseCase<RenderServiceRequestToken, Void> {

    private final MedIntakeRecordService medIntakeRecordService;

    @Override
    public Void execute(RenderServiceRequestToken input) {
        medIntakeRecordService.updateRecordDayViewCache(input.getAppUserUuid(), input.getReferenceDate());
        return null;
    }
}
