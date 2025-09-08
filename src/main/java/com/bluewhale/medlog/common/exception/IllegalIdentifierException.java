package com.bluewhale.medlog.common.exception;

import com.bluewhale.medlog.common.service.IdentifierConvertService;
import jakarta.persistence.EntityNotFoundException;

/**
 * {@link IdentifierConvertService} 의 구현체의 method 로 전달된 인자에 대해<br/>
 * 유효한 Entity 객체가 반환되지 않았을 경우 발생하는 예외.
 */
public class IllegalIdentifierException extends EntityNotFoundException {
    public IllegalIdentifierException(String message) {
        super(message);
    }
}
