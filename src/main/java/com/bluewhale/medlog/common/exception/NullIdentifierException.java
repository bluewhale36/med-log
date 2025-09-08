package com.bluewhale.medlog.common.exception;

import com.bluewhale.medlog.common.service.IdentifierConvertService;

/**
 * {@link IdentifierConvertService} 의 구현체에서, method 의 인자인<br/>
 * {@link com.bluewhale.medlog.common.value.AbstractUuid} 의 상속체 또는 {@link Long} 인 Id 값이<br/>
 * Null 로 전달되었을 경우 발생하는 예외.
 */
public class NullIdentifierException extends IllegalArgumentException {
    public NullIdentifierException(String message) {
        super(message);
    }
}
