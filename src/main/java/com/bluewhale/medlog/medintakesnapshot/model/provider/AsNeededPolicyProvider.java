package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.AsNeededReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class AsNeededPolicyProvider extends AbstractPolicyProvider {

    protected AsNeededPolicyProvider() {
        super(DoseFrequencyType.AS_NEEDED);
    }

    @Override
    protected boolean isNeedPreProcess() {
        return false;
    }



    @Override
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestToken prmToken) {
        return Optional.empty();
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {
        specificTracer.setReason(new AsNeededReason());

        return new PolicyEvaluateResult(
                null, requestToken.getMedId(), false, null, referenceDateTime.toLocalDate(), specificTracer
        );
    }
}
