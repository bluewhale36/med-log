package com.bluewhale.medlog.med.model.dosefrequency;

import com.bluewhale.medlog.med.model.dosefrequency.detail.*;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum DoseFrequencyType {
    EVERY_DAY("매일", "매일 같은 시각에 약을 복용합니다.", EveryDayDetail.class, EveryDayReason.class),
    CYCLICAL("주기적으로", "21일간 매일 복용하고 7일간 복용을 중단합니다.", CyclicalDetail.class, CyclicalReason.class),
    SPECIFIC_DAYS("특정 요일에", "월요일, 수요일에 복용합니다.", SpecificDaysDetail.class, SpecificDaysReason.class),
    INTERVAL("특정 간격으로", "3일에 한번 복용합니다.", IntervalDetail.class, IntervalReason.class),
    AS_NEEDED("필요에 따라", "필요할 때 복용합니다.", AsNeededDetail.class, AsNeededReason.class);

    private final String title;
    private final String placeholder;
    private final Class<? extends AbstractDoseFrequencyDetail> detailClass;
    private final Class<? extends AbstractEvaluateReason> reasonClass;

    public boolean supportsDetailClass(Class<? extends AbstractDoseFrequencyDetail> type) {
        return detailClass.isAssignableFrom(type);
    }

    public boolean supportsReasonClass(Class<? extends AbstractEvaluateReason> type) {
        return reasonClass.isAssignableFrom(type);
    }

}
