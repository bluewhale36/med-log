package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.EveryDayDetail;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.EveryDayReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class EveryDayPolicyProvider extends AbstractPolicyProvider {

    public EveryDayPolicyProvider() {
        super(DoseFrequencyType.EVERY_DAY);
    }

    @Override
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {
        specificTracer.setReason(new EveryDayReason());
        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), true, stdDateTime, stdDateTime.toLocalDate(), specificTracer
                requestToken.getAppUserId(), requestToken.getMedId(), true, referenceDateTime, referenceDateTime.toLocalDate(), specificTracer
        );
    }
}
