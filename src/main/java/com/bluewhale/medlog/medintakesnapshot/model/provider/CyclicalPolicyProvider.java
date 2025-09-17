package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.CyclicalDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.CyclicalReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
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
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestToken prmToken) {
        List<LocalTime> timeList = ((CyclicalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail())
                .getDoseTimeCountList().stream().map(DoseTimeCount::getDoseTime).toList();

        return Optional.of(timeList);
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {

        // DoseFrequencyDetail 을 CyclicalDetail 로 다운캐스팅
        CyclicalDetail detail = (CyclicalDetail) requestToken.getDoseFrequency().getDoseFrequencyDetail();

        int onDurationInDays = detail.getOnDurationInDays(),
                offDurationInDays = detail.getOffDurationInDays(),
                cycleInDays = onDurationInDays + offDurationInDays;

        long takenFor = ChronoUnit.DAYS.between(requestToken.getStartedOn(), referenceDateTime);

        long cycleCount = takenFor /cycleInDays, dayN = takenFor %cycleInDays;
        boolean isOnDuration = dayN < onDurationInDays;

        CyclicalReason reason = new CyclicalReason(takenFor +1L, cycleCount, dayN, isOnDuration);
        specificTracer.setReason(reason);

        return new PolicyEvaluateResult(
                requestToken.getAppUserId(), requestToken.getMedId(),
                isOnDuration, referenceDateTime, referenceDateTime.toLocalDate(), specificTracer
        );
    }
}
