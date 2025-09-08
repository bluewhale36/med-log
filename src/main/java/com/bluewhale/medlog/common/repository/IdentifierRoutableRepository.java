package com.bluewhale.medlog.common.repository;

import com.bluewhale.medlog.common.value.AbstractUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IdentifierRoutableRepository<E, ID, UUID extends AbstractUuid> extends JpaRepository<E, ID> {

    Optional<ID> findIdByUuid(UUID uuid);
    Optional<UUID> findUuidById(ID id);

}
