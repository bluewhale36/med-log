package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.value.AbstractUuid;
import java.util.Optional;
import java.util.function.Function;

/**
 * {@link IdentifierConvertService}의 골격 구현(skeletal implementation)을 제공하는 추상 클래스.
 * <p>
 * 템플릿 메서드 패턴을 사용하여 ID와 UUID 간 변환 로직의 공통적인 흐름(null 체크, 예외 처리 등)을 정의한다.
 * 하위 클래스는 특정 엔티티에 대한 조회 로직(finder)과 엔티티 이름을 제공함으로써 이 클래스를 확장해야 한다.
 *
 * @param <E> 변환 대상이 되는 엔티티 타입
 * @param <UUID> 변환에 사용될 {@link AbstractUuid}를 상속하는 값 객체 타입
 * @author bluewhale36
 * @since 2025-09-08
 */
public abstract class AbstractIdentifierConvertService<E, UUID extends AbstractUuid>
        implements IdentifierConvertService<UUID> {

    /**
     * 하위 클래스가 사용할 리포지토리 인스턴스를 반환한다.
     *
     * @return 특정 엔티티에 대한 {@link IdentifierRoutableRepository} 구현체
     */
    protected abstract IdentifierRoutableRepository<E, Long, UUID> getRepository();

    /**
     * Long ID를 사용하여 UUID를 조회하는 함수(메서드 참조)를 반환한다.
     * <p>
     * 예: {@code return specificRepository::findUuidById;}
     *
     * @return ID를 인자로 받아 Optional<UUID>를 반환하는 함수
     */
    protected abstract Function<Long, Optional<UUID>> uuidFinder();

    /**
     * UUID를 사용하여 Long ID를 조회하는 함수(메서드 참조)를 반환한다.
     * <p>
     * 예: {@code return specificRepository::findIdByUuid;}
     *
     * @return UUID를 인자로 받아 Optional<Long>을 반환하는 함수
     */
    protected abstract Function<UUID, Optional<Long>> idFinder();

    /**
     * 예외 메시지 생성에 사용될 엔티티의 이름을 반환한다.
     * <p>
     * 예: {@code return "AppUser";}
     *
     * @return 엔티티 클래스 이름 문자열
     */
    protected abstract String getEntityName();

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getIdByUuid(UUID uuid) {
        if (uuid == null) {
            throw new NullIdentifierException(String.format("Uuid Value for %s cannot be Null.", this.getEntityName()));
        }

        return idFinder().apply(uuid).orElseThrow(
                () -> new IllegalIdentifierException(String.format("%s with Uuid %s has not been found.", this.getEntityName(), uuid))
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUuidById(Long id) {
        if (id == null) {
            throw new NullIdentifierException(String.format("Id Value for %s cannot be Null.", this.getEntityName()));
        }

        return uuidFinder().apply(id).orElseThrow(
                () -> new IllegalIdentifierException(String.format("%s with id %s has not been found.",  this.getEntityName(), id))
        );
    }
}