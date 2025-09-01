package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.IntervalDetail;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.IntervalReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
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
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestMedToken prmToken) {
        Optional<List<LocalTime>> result;
        try {
            result = Optional.of(
                    ((IntervalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail()).getTimes()
            );
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {

        LocalDate startedOn = prmToken.getStartedOn();
        IntervalDetail detail = (IntervalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail();
        int interval = detail.getInterval();

        long takenFor = ChronoUnit.DAYS.between(startedOn, stdDateTime),
                takenCount = takenFor /interval +1,
                intervalDayN = takenFor %interval;
        boolean isOnDay = intervalDayN == 0;

        LocalDate previousDoseDate, nextDoseDate;
        if (isOnDay) {
            previousDoseDate = stdDateTime.toLocalDate().minusDays(interval);
            nextDoseDate = stdDateTime.toLocalDate().plusDays(interval);
        } else {
            previousDoseDate = stdDateTime.toLocalDate().minusDays(intervalDayN);
            nextDoseDate = stdDateTime.toLocalDate().plusDays(interval -intervalDayN);
        }

        specificTracer.setReason(
                new IntervalReason(takenFor +1, takenCount, intervalDayN, previousDoseDate, nextDoseDate, isOnDay)
        );

        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), isOnDay, stdDateTime, stdDateTime.toLocalDate(), specificTracer
        );
    }
}
