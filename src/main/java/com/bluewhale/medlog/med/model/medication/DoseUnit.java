package com.bluewhale.medlog.med.model.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum DoseUnit {
    MILLIGRAM("mg"),
    MICROGRAM("mcg"),
    GRAM("g"),
    MILLILITER("mL"),
    PERCENTAGE("%");

    private final String title;
}
