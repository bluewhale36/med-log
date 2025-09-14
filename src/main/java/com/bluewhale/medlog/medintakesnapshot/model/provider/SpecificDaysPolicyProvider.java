package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.Days;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.SpecificDaysDetail;
import com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount.DoseTimeCount;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.SpecificDaysReason;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
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
    protected Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestToken prmToken) {
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
    protected PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestToken requestToken, LocalDateTime referenceDateTime) {
        // 기준 일자의 요일
        Days referenceDay = Days.fromDayOfWeek(referenceDateTime.getDayOfWeek());

        SpecificDaysDetail detail = (SpecificDaysDetail) requestToken.getDoseFrequency().getDoseFrequencyDetail();
        List<SpecificDaysDetail.SpecificDaysSet> specificDaysSetList = detail.getSpecificDays();


        for (SpecificDaysDetail.SpecificDaysSet set : specificDaysSetList) {
            if (
                    set.getDayList().contains(referenceDay) && // 기준 일자의 요일이 포함되고
                    set.getDoseTimeCountList().stream()
                            .map(DoseTimeCount::getDoseTime)
                            .anyMatch(t -> t.equals(referenceDateTime.toLocalTime())) // 기준 일자의 시각이 존재하는 경우
            ) {
                specificTracer.setReason(
                        new SpecificDaysReason(referenceDay, true)
                );
                return new PolicyEvaluateResult(
                        requestToken.getAppUserId(), requestToken.getMedId(),
                        true, referenceDateTime, referenceDateTime.toLocalDate(), specificTracer
                );
            }
        }
        // 어느 세트에도 ReferenceDateTime 이 포함되지 않는 경우
        specificTracer.setReason(
                new SpecificDaysReason(referenceDay, false)
        );
        // 복용할 필요 없음
        return new PolicyEvaluateResult(
                requestToken.getAppUserId(), requestToken.getMedId(),
                false, null, referenceDateTime.toLocalDate(), specificTracer
        );
    }

}
