package com.bluewhale.medlog.appuser.dto;

import com.bluewhale.medlog.appuser.enums.HeightUnit;
import com.bluewhale.medlog.appuser.enums.WeightUnit;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class AppUserMetricLogDTO {

    private final Long usersMetricLogId;
    private final Float height;
    private final HeightUnit heightUnit;
    private final Float weight;
    private final WeightUnit weightUnit;
    private final LocalDateTime loggedAt;

    private final Float heightInCentimeters;
    private final Float weightInKilograms;


    public AppUserMetricLogDTO(Long usersMetricLogId, Float height, HeightUnit heightUnit, Float weight, WeightUnit weightUnit, LocalDateTime loggedAt) {
        this.usersMetricLogId = usersMetricLogId;
        this.height = height;
        this.heightUnit = heightUnit;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.loggedAt = loggedAt;

        this.heightInCentimeters = heightUnit.toCentimeters(height);
        this.weightInKilograms = weightUnit.toKilograms(weight);
    }
}
