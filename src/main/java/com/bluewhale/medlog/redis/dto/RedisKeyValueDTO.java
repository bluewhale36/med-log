package com.bluewhale.medlog.redis.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
@ToString
public class RedisKeyValueDTO {

    private final String key;
    private final String value;
    private Duration duration;
}
