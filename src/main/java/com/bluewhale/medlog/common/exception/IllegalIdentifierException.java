package com.bluewhale.medlog.common.exception;

import com.bluewhale.medlog.common.service.IdentifierConvertService;
import jakarta.persistence.EntityNotFoundException;

/**
 * {@link IdentifierConvertService}의 구현체에서 전달된 식별자(ID 또는 UUID)에 해당하는
 * 유효한 엔티티를 데이터베이스에서 찾을 수 없을 때 발생하는 예외.
 * <p>
 * JPA의 {@link EntityNotFoundException}을 상속받아, 식별자 변환 실패 상황에
 * 특화된 의미를 부여한다.
 *
 * @author bluewhale36
 * @since 2025-09-08
 * @see IdentifierConvertService
 */
public class IllegalIdentifierException extends EntityNotFoundException {
    public IllegalIdentifierException(String message) {
        super(message);
    }
}