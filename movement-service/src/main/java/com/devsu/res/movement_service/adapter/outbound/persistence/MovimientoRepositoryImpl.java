package com.devsu.res.movement_service.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.MovimientoEntity;
import com.devsu.res.movement_service.adapter.outbound.persistence.repository.MovimientoJpaRepository;
import com.devsu.res.movement_service.domain.model.Movimiento;
import com.devsu.res.movement_service.domain.port.outbound.MovimientoRepositoryPort;

@Repository
public class MovimientoRepositoryImpl implements MovimientoRepositoryPort {

    private final MovimientoJpaRepository movimientoJpaRepository;
    private final ModelMapper modelMapper;

    public MovimientoRepositoryImpl(MovimientoJpaRepository movimientoJpaRepository, ModelMapper modelMapper) {
        this.movimientoJpaRepository = movimientoJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Movimiento save(Movimiento movimiento) {
        MovimientoEntity entity = modelMapper.map(movimiento, MovimientoEntity.class);
        MovimientoEntity savedEntity = movimientoJpaRepository.save(entity);
        return modelMapper.map(savedEntity, Movimiento.class);
    }

    @Override
    public Optional<Movimiento> findById(UUID id) {
        return movimientoJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Movimiento.class));
    }

    @Override
    public List<Movimiento> findByCuentaId(UUID cuentaId) {
        return movimientoJpaRepository.findByCuentaId(cuentaId).stream()
                .map(entity -> modelMapper.map(entity, Movimiento.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        movimientoJpaRepository.deleteById(id);
    }
}