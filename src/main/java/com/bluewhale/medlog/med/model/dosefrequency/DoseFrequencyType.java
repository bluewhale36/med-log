package com.bluewhale.medlog.med.model.dosefrequency;

import com.bluewhale.medlog.med.model.dosefrequency.detail.*;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 복용 주기 유형을 나타내는 열거형입니다.
 *
 * <p>각 열거형 요소는 사용자에게 보여줄 제목(title), 입력 힌트(placeholder),
 * 해당 주기 상세 정보를 표현하는 {@code AbstractDoseFrequencyDetail}의 하위 클래스,
 * 그리고 평가(또는 사유)를 표현하는 {@code AbstractEvaluateReason}의 하위 클래스
 * 타입을 함께 보관합니다.</p>
 *
 * <p>이 열거형은 Lombok의 {@code @RequiredArgsConstructor}, {@code @Getter},
 * {@code @ToString}을 사용하여 생성자·접근자·문자열 표현을 자동 생성합니다.</p>
 */
@RequiredArgsConstructor
@Getter
@ToString
public enum DoseFrequencyType {
    /**
     * 매일 복용하는 경우.
     *
     * <p>title: "매일"
     * <br>placeholder: "매일 같은 시각에 약을 복용합니다."
     * <br>detailClass: {@link EveryDayDetail}
     * <br>reasonClass: {@link EveryDayReason}</p>
     */
    EVERY_DAY(
            "매일",
            "매일 같은 시각에 약을 복용합니다.",
            EveryDayDetail.class,
            EveryDayReason.class
    ),

    /**
     * 주기적으로 복용하는 경우 (예: 21일 복용 후 7일 중단).
     *
     * <p>title: "주기적으로"
     * <br>placeholder: "21일간 매일 복용하고 7일간 복용을 중단합니다."
     * <br>detailClass: {@link CyclicalDetail}
     * <br>reasonClass: {@link CyclicalReason}</p>
     */
    CYCLICAL(
            "주기적으로",
            "21일간 매일 복용하고 7일간 복용을 중단합니다.",
            CyclicalDetail.class,
            CyclicalReason.class
    ),

    /**
     * 특정 요일에만 복용하는 경우.
     *
     * <p>title: "특정 요일에"
     * <br>placeholder: "월요일, 수요일에 복용합니다."
     * <br>detailClass: {@link SpecificDaysDetail}
     * <br>reasonClass: {@link SpecificDaysReason}</p>
     */
    SPECIFIC_DAYS(
            "특정 요일에",
            "월요일, 수요일에 복용합니다.",
            SpecificDaysDetail.class,
            SpecificDaysReason.class
    ),

    /**
     * 일정 간격(예: 3일에 한 번)으로 복용하는 경우.
     *
     * <p>title: "특정 간격으로"
     * <br>placeholder: "3일에 한번 복용합니다."
     * <br>detailClass: {@link IntervalDetail}
     * <br>reasonClass: {@link IntervalReason}</p>
     */
    INTERVAL(
            "특정 간격으로",
            "3일에 한번 복용합니다.",
            IntervalDetail.class,
            IntervalReason.class
    ),

    /**
     * 필요 시 복용하는 경우 (필요에 따라).
     *
     * <p>title: "필요에 따라"
     * <br>placeholder: "필요할 때 복용합니다."
     * <br>detailClass: {@link AsNeededDetail}
     * <br>reasonClass: {@link AsNeededReason}</p>
     */
    AS_NEEDED(
            "필요에 따라",
            "필요할 때 복용합니다.",
            AsNeededDetail.class,
            AsNeededReason.class
    );

    /**
     * UI에 표시할 간단한 제목(예: "매일", "주기적으로").
     */
    private final String title;

    /**
     * 입력 또는 설정을 돕기 위한 설명(힌트) 문자열.
     */
    private final String placeholder;

    /**
     * 이 복용 주기 유형에 대응되는 상세 정보 모델 클래스.
     *
     * <p>이 타입은 {@code AbstractDoseFrequencyDetail}의 하위 클래스여야 합니다.
     */
    private final Class<? extends AbstractDoseFrequencyDetail> detailClass;

    /**
     * 이 복용 주기 유형에 대응되는 평가(사유) 모델 클래스.
     *
     * <p>이 타입은 {@code AbstractEvaluateReason}의 하위 클래스여야 합니다.</p>
     */
    private final Class<? extends AbstractEvaluateReason> reasonClass;

    /**
     * 지정한 상세 정보 클래스 타입이 이 열거형의 {@code detailClass}로부터 할당 가능한지 검사합니다.
     *
     * @param type 검사할 상세 정보 클래스 타입 ({@code AbstractDoseFrequencyDetail}의 하위 타입)
     * @return {@code true}이면 주어진 타입은 이 열거형의 상세 클래스와 호환됩니다.
     */
    public boolean supportsDetailClass(Class<? extends AbstractDoseFrequencyDetail> type) {
        return detailClass.isAssignableFrom(type);
    }

    /**
     * 지정한 평가(사유) 클래스 타입이 이 열거형의 {@code reasonClass}로부터 할당 가능한지 검사합니다.
     *
     * @param type 검사할 평가(사유) 클래스 타입 ({@code AbstractEvaluateReason}의 하위 타입)
     * @return {@code true}이면 주어진 타입은 이 열거형의 사유 클래스와 호환됩니다.
     */
    public boolean supportsReasonClass(Class<? extends AbstractEvaluateReason> type) {
        return reasonClass.isAssignableFrom(type);
    }

}