package com.bluewhale.medlog.common.repository;

import com.bluewhale.medlog.common.service.AbstractIdentifierConvertService;
import com.bluewhale.medlog.common.value.AbstractUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * {@link AbstractIdentifierConvertService}에서 사용될 리포지토리가 반드시 제공해야 하는
 * 식별자 변환 조회 메서드를 정의하는 제네릭 인터페이스.
 * <p>
 * 이 인터페이스를 상속하는 리포지토리는 ID ↔ UUID 간의 상호 조회를 보장하며,
 * 컴파일 시점의 타입 안정성을 제공한다.
 *
 * @param <E> 리포지토리가 다루는 엔티티 타입
 * @param <ID> 엔티티의 Primary Key 타입 (주로 Long)
 * @param <UUID> 엔티티의 비즈니스 식별자(UUID) 타입
 * @author bluewhale36
 * @since 2025-09-08
 * @see AbstractIdentifierConvertService
 */
@NoRepositoryBean
public interface IdentifierRoutableRepository<E, ID, UUID extends AbstractUuid> extends JpaRepository<E, ID> {

    /**
     * UUID를 통해 엔티티의 Primary Key(ID)를 조회한다.
     * 성능을 위해 엔티티 전체가 아닌 ID 필드만 조회(projection)하는 것을 권장한다.
     *
     * @param uuid 조회할 UUID 값 객체
     * @return 조회된 ID를 담은 Optional 객체. 결과가 없으면 Optional.empty()
     */
    Optional<ID> findIdByUuid(UUID uuid);

    /**
     * Primary Key(ID)를 통해 엔티티의 UUID를 조회한다.
     * 성능을 위해 엔티티 전체가 아닌 UUID 필드만 조회(projection)하는 것을 권장한다.
     *
     * @param id 조회할 Primary Key 값
     * @return 조회된 UUID를 담은 Optional 객체. 결과가 없으면 Optional.empty()
     */
    Optional<UUID> findUuidById(ID id);

}