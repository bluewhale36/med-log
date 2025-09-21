package com.bluewhale.medlog.redis.service;

import com.bluewhale.medlog.redis.config.RedisCacheConfig;
import com.bluewhale.medlog.redis.handler.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisHandler handler;
    private final RedisCacheConfig config;

    /**
     * Redis 단일 데이터 값을 등록/수정합니다.
     *
     * @param key   : redis key
     * @param value : redis value
     * @return {int} 성공(1), 실패(0)
     */
    public int setSingleData(String key, Object value) {
        return handler.executeOperation(() -> handler.getValueOperations().set(key, value));
    }

    /**
     * Redis 단일 데이터 값을 등록/수정합니다.(duration 값이 존재하면 메모리 상 유효시간을 지정합니다.)
     *
     * @param key      : redis key
     * @param value:   : redis value
     * @param duration : redis 값 메모리 상의 유효시간.
     * @return {int} 성공(1), 실패(0)
     */
    public int setSingleData(String key, Object value, Duration duration) {
        return handler.executeOperation(() -> handler.getValueOperations().set(key, value, duration));
    }

    /**
     * Redis 키를 기반으로 단일 데이터의 값을 조회합니다.
     *
     * @param key : redis key
     * @return {String} redis value 값 반환 or 미 존재시 null 반환
     */
    public String getSingleData(String key) {
        if (handler.getValueOperations().get(key) == null) return "";
        return String.valueOf(handler.getValueOperations().get(key));
    }

    /**
     * Redis 키를 기반으로 단일 데이터의 값을 삭제합니다.
     *
     * @param key : redis key
     * @return {int} 성공(1), 실패(0)
     */
    public int deleteSingleData(String key) {
        return handler.executeOperation(() -> config.redisTemplate().delete(key));
    }
}
