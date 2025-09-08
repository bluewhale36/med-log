package com.bluewhale.medlog.common.mapper;

import java.util.List;

public interface Mapper<E, DTO> {

    E toEntity(DTO dto, boolean isCreating);
    DTO toDTO(E entity);

    default List<DTO> toDTOList(List<E> entityList) {
        return entityList.stream().map(this::toDTO).toList();
    }

    default List<E> toEntityList(List<DTO> dtoList, boolean isCreating) {
        return dtoList.stream().map(d -> this.toEntity(d, isCreating)).toList();
    }
}
