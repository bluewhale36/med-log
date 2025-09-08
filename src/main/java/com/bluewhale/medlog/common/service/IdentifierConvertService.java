package com.bluewhale.medlog.common.service;

import com.bluewhale.medlog.common.value.AbstractUuid;

public interface IdentifierConvertService<UUID extends AbstractUuid> {

    /**
     * 각 Domain 의 Repository 에 findIdBy(Domain)Uuid method 정의하고 이를 호출하여 사용할 것.
     * @param uuid
     * @return
     */
    public Long getIdByUuid(UUID uuid);

    public UUID getUuidById(Long id);

}
