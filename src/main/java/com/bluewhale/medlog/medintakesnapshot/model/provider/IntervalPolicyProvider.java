package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.IntervalDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
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
        List<DoseTimeCount> doseTimeCountList =
                prmToken.getDoseFrequency().getDoseFrequencyDetail().doseTimeCountList().orElse(null);
        return doseTimeCountList == null ?
                Optional.empty() :
                Optional.of(
                        doseTimeCountList.stream().map(DoseTimeCount::getDoseTime).toList()
                );
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
