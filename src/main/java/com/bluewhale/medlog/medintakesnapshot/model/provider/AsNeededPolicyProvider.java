package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.AsNeededReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestMedToken prmToken) {
        return Optional.empty();
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {
        specificTracer.setReason(new AsNeededReason());

        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), false, null, stdDateTime.toLocalDate(), specificTracer
        );
    }
}
