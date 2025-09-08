package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.value.AbstractUuid;

public interface IdentifierConvertService<UUID extends AbstractUuid> {

    public Long getIdByUuid(UUID uuid);
    public UUID getUuidById(Long id);

}
