package com.bluewhale.medlog.med.model.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum MedForm {

    COMMON_FORMS("일반적인 형태"),
    MORE_FORMS("다른 형태");

    private final String title;

}
