package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.IntervalDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.IntervalReason;
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
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestToken prmToken) {
        Optional<List<LocalTime>> result;
        try {
            List<LocalTime> timeList = ((IntervalDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail())
                    .getDoseTimeCountList().stream().map(DoseTimeCount::getDoseTime).toList();
            result = Optional.of(timeList);
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {

        LocalDate startedOn = requestToken.getStartedOn();
        IntervalDetail detail = (IntervalDetail) requestToken.getDoseFrequency().getDoseFrequencyDetail();
        int interval = detail.getInterval();

        long takenFor = ChronoUnit.DAYS.between(startedOn, referenceDateTime),
                takenCount = takenFor /interval +1,
                intervalDayN = takenFor %interval;
        boolean isOnDay = intervalDayN == 0;

        LocalDate previousDoseDate, nextDoseDate;
        if (isOnDay) {
            previousDoseDate = referenceDateTime.toLocalDate().minusDays(interval);
            nextDoseDate = referenceDateTime.toLocalDate().plusDays(interval);
        } else {
            previousDoseDate = referenceDateTime.toLocalDate().minusDays(intervalDayN);
            nextDoseDate = referenceDateTime.toLocalDate().plusDays(interval -intervalDayN);
        }

        specificTracer.setReason(
                new IntervalReason(takenFor +1, takenCount, intervalDayN, previousDoseDate, nextDoseDate, isOnDay)
        );

        return new PolicyEvaluateResult(
                requestToken.getAppUserId(), requestToken.getMedId(), isOnDay, referenceDateTime, referenceDateTime.toLocalDate(), specificTracer
        );
    }
}
