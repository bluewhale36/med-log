package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.value.AbstractUuid;

/**
 * 데이터베이스의 내부 식별자(Long ID)와 외부에 노출되는 비즈니스 식별자(UUID) 간의 상호 변환을 위한 공통 계약을 정의하는 인터페이스.
 * <p>
 * 이 인터페이스를 구현하는 클래스는 특정 도메인 엔티티에 대한 ID와 UUID 변환 로직을 제공해야 한다.
 *
 * @param <UUID> {@link AbstractUuid}를 상속하는 타입-세이프(type-safe) UUID 값 객체 타입
 * @author bluewhale36
 * @since 2025-09-08
 */
public interface IdentifierConvertService<UUID extends AbstractUuid> {

    /**
     * 주어진 UUID에 해당하는 엔티티의 Long ID를 반환한다.
     *
     * @param uuid 조회할 엔티티의 UUID 값 객체.
     * @return 데이터베이스 상의 Long 타입 ID.
     * @throws com.bluewhale.medlog.common.exception.NullIdentifierException uuid가 null일 경우 발생.
     * @throws com.bluewhale.medlog.common.exception.IllegalIdentifierException 주어진 uuid에 해당하는 엔티티가 없을 경우 발생.
     */
    Long getIdByUuid(UUID uuid);

    /**
     * 주어진 Long ID에 해당하는 엔티티의 UUID를 반환한다.
     *
     * @param id 조회할 엔티티의 Long 타입 ID.
     * @return 해당 엔티티의 UUID 값 객체.
     * @throws com.bluewhale.medlog.common.exception.NullIdentifierException id가 null일 경우 발생.
     * @throws com.bluewhale.medlog.common.exception.IllegalIdentifierException 주어진 id에 해당하는 엔티티가 없을 경우 발생.
     */
    UUID getUuidById(Long id);

}