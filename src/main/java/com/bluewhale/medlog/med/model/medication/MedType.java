package com.bluewhale.medlog.med.model.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 약물의 종류를 나타내는 열거형(Enum)입니다.
 *
 * <p>각 열거 값은 UI에 표시할 한글 제목(title),
 * 복용 시 수량 단위를 나타내는 {@code intakeCountUnit} (예: "개", "회", "방울"),
 * 복용 동작을 나타내는 명사형 {@code intakeActionNoun} (예: "복용", "바름", "사용"),
 * 그리고 동작을 설명하는 문장형 {@code intakeActionVerb} (예: "복용합니다.", "바릅니다.")을 보관합니다.
 * 또한 약의 형태 그룹을 나타내는 {@link MedForm} 값을 함께 보관합니다.</p>
 *
 * <p>이 열거형의 값들은 UI 표시문구 생성(예: "1개 복용", "2회 바름")과
 * 사용자가 복용 동작을 이해하기 위한 텍스트 조합에 사용됩니다.</p>
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
public enum MedType {

    /**
     * 캡슐 형태의 약물.
     *
     * <ul>
     *   <li>title: "캡슐"</li>
     *   <li>intakeCountUnit: "캡슐"</li>
     *   <li>intakeActionNoun: "복용"</li>
     *   <li>intakeActionVerb: "복용합니다."</li>
     *   <li>medForm: {@link MedForm#COMMON_FORMS}</li>
     * </ul>
     */
    CAPSULE(
            "캡슐",
            "캡슐",
            "복용",
            "복용합니다.",
            MedForm.COMMON_FORMS
    ),

    /**
     * 알약(정제) 형태의 약물.
     *
     * <ul>
     *   <li>title: "알약"</li>
     *   <li>intakeCountUnit: "개"</li>
     *   <li>intakeActionNoun: "복용"</li>
     *   <li>intakeActionVerb: "복용합니다."</li>
     *   <li>medForm: {@link MedForm#COMMON_FORMS}</li>
     * </ul>
     */
    TABLET(
            "알약",
            "개",
            "복용",
            "복용합니다.",
            MedForm.COMMON_FORMS
    ),

    /**
     * 액체 형태의 약물(시럽 등).
     *
     * <ul>
     *   <li>title: "액체"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "복용"</li>
     *   <li>intakeActionVerb: "복용합니다."</li>
     *   <li>medForm: {@link MedForm#COMMON_FORMS}</li>
     * </ul>
     */
    LIQUID(
            "액체",
            "회",
            "복용",
            "복용합니다.",
            MedForm.COMMON_FORMS
    ),

    /**
     * 국소적으로 바르는 약물(연고/로션 등 포함).
     *
     * <ul>
     *   <li>title: "국소 약물"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#COMMON_FORMS}</li>
     * </ul>
     */
    TOPICAL(
            "국소 약물",
            "회",
            "바름",
            "바릅니다.",
            MedForm.COMMON_FORMS
    ),

    /**
     * 크림 형태의 약물.
     *
     * <ul>
     *   <li>title: "크림"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    CREAM(
            "크림",
            "회",
            "바름",
            "바릅니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 의료 기기(흡입기 등) 또는 기타 장치류.
     *
     * <ul>
     *   <li>title: "기기"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "사용"</li>
     *   <li>intakeActionVerb: "사용합니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    DEVICE(
            "기기",
            "회",
            "사용",
            "사용합니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 점안(안약) 형태.
     *
     * <ul>
     *   <li>title: "안약"</li>
     *   <li>intakeCountUnit: "방울"</li>
     *   <li>intakeActionNoun: "투여"</li>
     *   <li>intakeActionVerb: "투여합니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    DROPS(
            "안약",
            "방울",
            "투여",
            "투여합니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 거품형 제형.
     *
     * <ul>
     *   <li>title: "거품"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    FOAM(
            "거품",
            "회",
            "바름",
            "바릅니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 젤 형태의 약물.
     *
     * <ul>
     *   <li>title: "젤"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    GEL(
            "젤",
            "회",
            "바름",
            "바릅니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 흡입기 형태.
     *
     * <ul>
     *   <li>title: "흡입"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "흡입"</li>
     *   <li>intakeActionVerb: "흡입합니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    INHALER(
            "흡입",
            "회",
            "흡입",
            "흡입합니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 주사 형태.
     *
     * <ul>
     *   <li>title: "주사"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "주사"</li>
     *   <li>intakeActionVerb: "주사합니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    INJECTION(
            "주사",
            "회",
            "주사",
            "주사합니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 로션 형태.
     *
     * <ul>
     *   <li>title: "로션"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    LOTION(
            "로션",
            "회",
            "바름",
            "바릅니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 연고 형태.
     *
     * <ul>
     *   <li>title: "연고"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "바름"</li>
     *   <li>intakeActionVerb: "바릅니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    OINTMENT(
            "연고",
            "회",
            "바름",
            "바릅니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 패치 형태.
     *
     * <ul>
     *   <li>title: "패치"</li>
     *   <li>intakeCountUnit: "개"</li>
     *   <li>intakeActionNoun: "붙임"</li>
     *   <li>intakeActionVerb: "붙입니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    PATCH(
            "패치",
            "개",
            "붙임",
            "붙입니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 파우더(분말) 형태.
     *
     * <ul>
     *   <li>title: "파우더"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "뿌림"</li>
     *   <li>intakeActionVerb: "뿌립니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    POWDER(
            "파우더",
            "회",
            "뿌림",
            "뿌립니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 스프레이 형태.
     *
     * <ul>
     *   <li>title: "스프레이"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "뿌림"</li>
     *   <li>intakeActionVerb: "뿌립니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    SPRAY(
            "스프레이",
            "회",
            "뿌림",
            "뿌립니다.",
            MedForm.MORE_FORMS
    ),
    /**
     * 좌약 형태.
     *
     * <ul>
     *   <li>title: "좌약"</li>
     *   <li>intakeCountUnit: "회"</li>
     *   <li>intakeActionNoun: "투여"</li>
     *   <li>intakeActionVerb: "투여합니다."</li>
     *   <li>medForm: {@link MedForm#MORE_FORMS}</li>
     * </ul>
     */
    SUPPOSITORY(
            "좌약",
            "회",
            "투여",
            "투여합니다.",
            MedForm.MORE_FORMS
    );

    /**
     * UI에 표시할 한글 제목 (예: "캡슐", "알약").
     */
    private final String title;
    /**
     * 복용 시 수량 단위 (예: "캡슐", "개", "회", "방울").
     * UI에서 숫자와 결합하여 "1개", "2회" 등의 표현을 만들 때 사용됩니다.
     */
    private final String intakeCountUnit;
    /**
     * 복용 동작을 설명하는 명사형 (예: "복용", "바름", "사용").
     * 수량 단위와 결합해 "1개 복용" 같은 짧은 표현을 만들 때 사용됩니다.
     */
    private final String intakeActionNoun;
    /**
     * 복용 동작을 설명하는 문장형/동사형 표현 (예: "복용합니다.", "바릅니다.").
     * 상세 안내 문구나 알림 텍스트에 사용됩니다.
     */
    private final String intakeActionVerb;
    /**
     * 약의 형태 그룹을 나타내는 값.
     * {@link MedForm#COMMON_FORMS} 또는 {@link MedForm#MORE_FORMS} 등으로 분류됩니다.
     */
    private final MedForm medForm;
}
