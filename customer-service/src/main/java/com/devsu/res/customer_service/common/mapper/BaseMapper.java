package com.devsu.res.customer_service.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase base para convertir entre entidades y DTOs.
 * Se debe extender para cada par Entity-DTO específico, sobrescribiendo
 * los métodos getEntityClass() y getDtoClass().
 *
 * @param <Entity> Tipo de entidad.
 * @param <DTO>    Tipo de Data Transfer Object.
 */
public abstract class BaseMapper<Entity, DTO> {

    protected ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DTO entityToDto(Entity entity) {
        return modelMapper.map(entity, getDtoClass());
    }

    public Entity dtoToEntity(DTO dto) {
        return modelMapper.map(dto, getEntityClass());
    }

    protected abstract Class<Entity> getEntityClass();

    protected abstract Class<DTO> getDtoClass();

    public List<DTO> entityListToDtoList(List<Entity> entityList) {
        return entityList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<Entity> dtoListToEntityList(List<DTO> dtoList) {
        return dtoList.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}