package com.bluewhale.medlog.common.exception;

import com.bluewhale.medlog.common.service.IdentifierConvertService;

/**
 * {@link IdentifierConvertService}의 구현체 메서드에 인자로 전달된 식별자(ID 또는 UUID)가
 * null 값일 경우 발생하는 예외.
 * <p>
 * 이는 잘못된 API 호출이나 내부 로직 오류를 빠르게 식별하기 위한 Fail-fast 전략의 일환이다.
 *
 * @author bluewhale36
 * @since 2025-09-08
 * @see IdentifierConvertService
 */
public class NullIdentifierException extends IllegalArgumentException {
    public NullIdentifierException(String message) {
        super(message);
    }
}