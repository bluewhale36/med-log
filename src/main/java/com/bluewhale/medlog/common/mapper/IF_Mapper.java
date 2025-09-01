package com.bluewhale.medlog.common.mapper;

import java.util.List;

public interface IF_Mapper<ENTITY, DTO> {

    ENTITY toEntity(DTO dto, boolean isCreating);
    DTO toDTO(ENTITY entity);

    default List<DTO> toDTOList(List<ENTITY> entityList) {
        return entityList.stream().map(this::toDTO).toList();
    }

    default List<ENTITY> toEntityList(List<DTO> dtoList, boolean isCreating) {
        return dtoList.stream().map(d -> this.toEntity(d, isCreating)).toList();
    }
}
