package com.bluewhale.medlog.med.model.dosefrequency.detail.dosetimecount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@ToString
public class DoseTimeCount {

    private final LocalTime doseTime;
    private final Integer doseCount;

    @JsonCreator
    public DoseTimeCount(
            @JsonProperty("doseTime")
            @JsonSerialize(using = LocalTimeSerializer.class)
            @JsonDeserialize(using = LocalTimeDeserializer.class)
            LocalTime doseTime,

            @JsonProperty("doseCount") Integer doseCount
    ) {
        this.doseTime = doseTime;
        this.doseCount = doseCount != null ? doseCount : 1;
    }
}
