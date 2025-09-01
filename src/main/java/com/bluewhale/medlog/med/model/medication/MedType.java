package com.bluewhale.medlog.med.model.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum MedType {

    CAPSULE("캡슐", MedForm.COMMON_FORMS),
    TABLET("알약", MedForm.COMMON_FORMS),
    LIQUID("액체", MedForm.COMMON_FORMS),
    TOPICAL("국소 약물", MedForm.COMMON_FORMS),

    CREAM("크림", MedForm.MORE_FORMS),
    DEVICE("기기", MedForm.MORE_FORMS),
    DROPS("안약", MedForm.MORE_FORMS),
    FOAM("거품", MedForm.MORE_FORMS),
    GEL("젤", MedForm.MORE_FORMS),
    INHALER("흡입", MedForm.MORE_FORMS),
    INJECTION("주사", MedForm.MORE_FORMS),
    LOTION("로션", MedForm.MORE_FORMS),
    OINTMENT("연고", MedForm.MORE_FORMS),
    PATCH("패치", MedForm.MORE_FORMS),
    POWDER("파우더", MedForm.MORE_FORMS),
    SPRAY("스프레이", MedForm.MORE_FORMS),
    SUPPOSITORY("좌약", MedForm.MORE_FORMS);

    private final String title;
    private final MedForm medForm;
}
