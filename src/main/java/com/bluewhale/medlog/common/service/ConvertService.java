package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.value.AbstractUuid;

public interface ConvertService<E, UUID extends AbstractUuid> {

    public Long getIdByUuid(UUID uuid);

    public E getEntityByUuid(UUID uuid);

    public UUID getUuidById(Long id);


}
