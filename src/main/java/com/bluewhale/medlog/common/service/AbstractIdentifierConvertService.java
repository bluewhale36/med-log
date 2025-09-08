package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.exception.IllegalIdentifierException;
import com.bluewhale.medlog.common.exception.NullIdentifierException;
import com.bluewhale.medlog.common.repository.IdentifierRoutableRepository;
import com.bluewhale.medlog.common.value.AbstractUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractIdentifierConvertService<E, UUID extends AbstractUuid>
        implements IdentifierConvertService<UUID> {

    protected abstract IdentifierRoutableRepository<E, Long, UUID> getRepository();

    protected abstract Function<Long, Optional<UUID>> uuidFinder();

    protected abstract Function<UUID, Optional<Long>> idFinder();

    protected abstract String getEntityName();

    @Override
    public Long getIdByUuid(UUID uuid) {
        if (uuid == null) {
            throw new NullIdentifierException(String.format("Uuid Value for %s cannot be Null.", this.getEntityName()));
        }

        return idFinder().apply(uuid).orElseThrow(
                () -> new IllegalIdentifierException(String.format("%s with Uuid %s has not been found.", this.getEntityName(), uuid))
        );
    }

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
