package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.IntervalDetail;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.IntervalReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class IntervalPolicyProvider extends AbstractPolicyProvider {

    public IntervalPolicyProvider() {
        super(DoseFrequencyType.INTERVAL);
    }

    @Override
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {

        LocalDate startedOn = prmToken.getStartedOn();
        IntervalDetail detail = (IntervalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail();
        LocalDate startedOn = requestToken.getStartedOn();
        IntervalDetail detail = (IntervalDetail) requestToken.getDoseFrequency().getDoseFrequencyDetail();
        int interval = detail.getInterval();

        long takenFor = ChronoUnit.DAYS.between(startedOn, stdDateTime),
        long takenFor = ChronoUnit.DAYS.between(startedOn, referenceDateTime),
                takenCount = takenFor /interval +1,
                intervalDayN = takenFor %interval;
        boolean isOnDay = intervalDayN == 0;

        LocalDate previousDoseDate, nextDoseDate;
        if (isOnDay) {
            previousDoseDate = stdDateTime.toLocalDate().minusDays(interval);
            nextDoseDate = stdDateTime.toLocalDate().plusDays(interval);
            previousDoseDate = referenceDateTime.toLocalDate().minusDays(interval);
            nextDoseDate = referenceDateTime.toLocalDate().plusDays(interval);
        } else {
            previousDoseDate = stdDateTime.toLocalDate().minusDays(intervalDayN);
            nextDoseDate = stdDateTime.toLocalDate().plusDays(interval -intervalDayN);
            previousDoseDate = referenceDateTime.toLocalDate().minusDays(intervalDayN);
            nextDoseDate = referenceDateTime.toLocalDate().plusDays(interval -intervalDayN);
        }

        specificTracer.setReason(
                new IntervalReason(takenFor +1, takenCount, intervalDayN, previousDoseDate, nextDoseDate, isOnDay)
        );

        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), isOnDay, stdDateTime, stdDateTime.toLocalDate(), specificTracer
                requestToken.getAppUserId(), requestToken.getMedId(), isOnDay, referenceDateTime, referenceDateTime.toLocalDate(), specificTracer
        );
    }
}
