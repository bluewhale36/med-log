package com.bluewhale.medlog.common.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * UUID 문자열을 캡슐화하여 타입 안정성을 제공하는 값 객체(Value Object)의 추상 기반 클래스.
 * <p>
 * 이 클래스를 상속하는 하위 클래스는 각 도메인에 특화된 UUID 타입을 정의할 수 있다.
 * (예: {@code AppUserUuid}, {@code MedUuid})
 * 생성 시점에서 UUID 형식의 유효성을 검사하여 객체의 불변성과 유효성을 보장한다.
 *
 * @author bluewhale36
 * @since 2025-09-08
 */
@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractUuid {

    private final String value;

    /**
     * UUID 문자열을 인자로 받아 유효성을 검증하고 내부 필드에 저장한다.
     *
     * @param value 유효한 형식의 UUID 문자열
     * @throws IllegalArgumentException 전달된 문자열이 유효한 UUID 형식이 아닐 경우
     */
    protected AbstractUuid(String value) {
        validate(value);
        this.value = value;
    }

    /**
     * 주어진 문자열이 UUID 형식에 맞는지 검증한다.
     *
     * @param uuid 검증할 UUID 문자열
     * @throws IllegalArgumentException 문자열이 유효하지 않을 경우
     */
    protected void validate(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID: " + uuid);
        }
    }
}