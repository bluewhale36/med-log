package com.bluewhale.medlog.med.model.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum MedType {

    CAPSULE(
            "캡슐",
            "캡슐",
            "복용",
            "복용합니다",
            MedForm.COMMON_FORMS
    ),
    TABLET(
            "알약",
            "개",
            "복용",
            "복용합니다",
            MedForm.COMMON_FORMS
    ),
    LIQUID(
            "물약",
            "회",
            "복용",
            "복용합니다",
            MedForm.COMMON_FORMS
    ),
    TOPICAL(
            "국소 약물",
            "회",
            "도포",
            "도포합니다",
            MedForm.COMMON_FORMS
    ),

    CREAM(
            "크림",
            "회",
            "도포",
            "도포합니다",
            MedForm.MORE_FORMS
    ),
    DEVICE(
            "기기",
            "회",
            "사용",
            "사용합니다",
            MedForm.MORE_FORMS
    ),
    DROPS(
            "안약",
            "회",
            "점안",
            "점안합니다",
            MedForm.MORE_FORMS
    ),
    FOAM(
            "거품",
            "회",
            "도포",
            "도포합니다",
            MedForm.MORE_FORMS
    ),
    GEL(
            "젤",
            "회",
            "도포",
            "도포합니다",
            MedForm.MORE_FORMS
    ),
    INHALER(
            "흡입",
            "회",
            "흡입",
            "흡입합니다",
            MedForm.MORE_FORMS
    ),
    INJECTION(
            "주사",
            "회",
            "투여",
            "투여합니다",
            MedForm.MORE_FORMS
    ),
    LOTION(
            "로션",
            "회",
            "도포",
            "도포합니다",
            MedForm.MORE_FORMS
    ),
    OINTMENT(
            "연고",
            "회",
            "도포",
            "도포합니다",
            MedForm.MORE_FORMS
    ),
    PATCH(
            "패치",
            "개",
            "부착",
            "부착합니다",
            MedForm.MORE_FORMS
    ),
    POWDER(
            "파우더",
            "회",
            "사용",
            "사용합니다",
            MedForm.MORE_FORMS
    ),
    SPRAY(
            "스프레이",
            "회",
            "분사",
            "분사합니다",
            MedForm.MORE_FORMS
    ),
    SUPPOSITORY(
            "좌약",
            "개",
            "투여",
            "투여합니다",
            MedForm.MORE_FORMS
    );

    private final String title;
    private final String countUnit;
    private final String doseActionInNoun;
    private final String doseActionInVerb;
    private final MedForm medForm;
}
