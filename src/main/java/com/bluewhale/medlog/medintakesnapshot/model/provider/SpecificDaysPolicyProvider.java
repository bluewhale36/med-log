package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.SpecificDaysDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.timecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.SpecificDaysReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class SpecificDaysPolicyProvider extends AbstractPolicyProvider {

    public SpecificDaysPolicyProvider() {
        super(DoseFrequencyType.SPECIFIC_DAYS);
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
        Days stdDay = Days.fromDayOfWeek(stdDateTime.getDayOfWeek());

        SpecificDaysDetail detail = (SpecificDaysDetail) prmToken.getDoseFrequency().getDoseFrequencyDetail();
        List<SpecificDaysDetail.SpecificDaysSet> specificDaysSetList = detail.getSpecificDays();


        for (SpecificDaysDetail.SpecificDaysSet set : specificDaysSetList) {
            if (set.getDaysList().contains(stdDay) && set.getDoseTimeCountList().contains(stdDateTime.toLocalTime())) {
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
