package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.CyclicalDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.CyclicalReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class CyclicalPolicyProvider extends AbstractPolicyProvider {

    public CyclicalPolicyProvider() {
        super(DoseFrequencyType.CYCLICAL);
    }

    @Override
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestMedToken prmToken) {
        List<DoseTimeCount> doseTimeCountList =
                ((CyclicalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail())
                        .doseTimeCountList().orElse(null);

        return doseTimeCountList == null ?
                Optional.empty() :
                Optional.of(
                        doseTimeCountList.stream().map(DoseTimeCount::getDoseTime).toList()
                );
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {

        CyclicalDetail detail = (CyclicalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail();

        int onDurationInDays = detail.getOnDurationInDays(),
                offDurationInDays = detail.getOffDurationInDays(),
                cycleInDays = onDurationInDays + offDurationInDays;

        long takenFor = ChronoUnit.DAYS.between(prmToken.getStartedOn(), stdDateTime);

        long cycleCount = takenFor /cycleInDays, dayN = takenFor %cycleInDays;
        boolean isOnDuration = dayN < onDurationInDays;

        CyclicalReason reason = new CyclicalReason(takenFor +1L, cycleCount, dayN, isOnDuration);
        specificTracer.setReason(reason);

        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), isOnDuration, stdDateTime, stdDateTime.toLocalDate(), specificTracer
        );
    }
}
