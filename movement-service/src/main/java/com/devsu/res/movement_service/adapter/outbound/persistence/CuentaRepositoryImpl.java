package com.devsu.res.movement_service.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.CuentaEntity;
import com.devsu.res.movement_service.adapter.outbound.persistence.repository.CuentaJpaRepository;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.model.Cuenta;
import com.devsu.res.movement_service.domain.port.outbound.CuentaRepositoryPort;

@Repository
public class CuentaRepositoryImpl extends BaseMapper<CuentaEntity, Cuenta> implements CuentaRepositoryPort {

    private final CuentaJpaRepository cuentaJpaRepository;

    public CuentaRepositoryImpl(CuentaJpaRepository cuentaJpaRepository, ModelMapper modelMapper) {
        this.cuentaJpaRepository = cuentaJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaEntity entity = this.dtoToEntity(cuenta);
        CuentaEntity savedEntity = cuentaJpaRepository.save(entity);
        return this.entityToDto(savedEntity);
    }

    @Override
    public Optional<Cuenta> findById(UUID id) {
        return cuentaJpaRepository.findById(id)
                .map(entity -> this.entityToDto(entity));
    }

    @Override
    public List<Cuenta> findAll() {
        var cuentas = cuentaJpaRepository.findAll();
        return this.entityListToDtoList(cuentas);
    }

    @Override
    public void deleteById(UUID id) {
        cuentaJpaRepository.deleteById(id);
    }

    @Override
    public List<Cuenta> findAccountsWithMovementsByClientAndDate(UUID clienteId, LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        List<CuentaEntity> entities = cuentaJpaRepository.findCuentasWithMovimientosByClienteAndFecha(clienteId,
                fechaInicio, fechaFin);
        return this.entityListToDtoList(entities);
    }

    @Override
    public List<Cuenta> findByClienteId(UUID clienteId) {
        List<CuentaEntity> entities = cuentaJpaRepository.findByClienteIdWithMovimientos(clienteId);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Cuenta.class))
                .collect(Collectors.toList());
    }

    @Override
    protected Class<CuentaEntity> getEntityClass() {
        return CuentaEntity.class;
    }

    @Override
    protected Class<Cuenta> getDtoClass() {
        return Cuenta.class;
    }

}