package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.SpecificDaysDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.SpecificDaysReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SpecificDaysPolicyProvider extends AbstractPolicyProvider {

    public SpecificDaysPolicyProvider() {
        super(DoseFrequencyType.SPECIFIC_DAYS);
    }

    @Override
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestMedToken prmToken) {
        Optional<List<LocalTime>> result;
        try {
            List<LocalTime> timeList = new ArrayList<>();
            ((SpecificDaysDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail()).getSpecificDays().stream()
                    .map(SpecificDaysDetail.SpecificDaysSet::getDoseTimeCountList)
                    .flatMap(Collection::stream)
                    .map(DoseTimeCount::getDoseTime)
                    .forEach(timeList::add);
            result = Optional.of(timeList);
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken prmToken, LocalDateTime stdDateTime) {
        Days stdDay = Days.fromDayOfWeek(stdDateTime.getDayOfWeek());

        SpecificDaysDetail detail = (SpecificDaysDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail();
        List<SpecificDaysDetail.SpecificDaysSet> specificDaysSetList = detail.getSpecificDays();


        for (SpecificDaysDetail.SpecificDaysSet set : specificDaysSetList) {
            if (set.getDays().contains(stdDay) && set.getDoseTimeCountList().contains(stdDateTime.toLocalTime())) {
                specificTracer.setReason(
                        new SpecificDaysReason(stdDay, true)
                );
                return new PolicyEvaluateResult(
                        null, prmToken.getMedId(), true, stdDateTime, stdDateTime.toLocalDate(), specificTracer
                );
            }
        }
        specificTracer.setReason(
                new SpecificDaysReason(stdDay, false)
        );
        return new PolicyEvaluateResult(
                null, prmToken.getMedId(), false, null, stdDateTime.toLocalDate(), specificTracer
        );
    }

}
