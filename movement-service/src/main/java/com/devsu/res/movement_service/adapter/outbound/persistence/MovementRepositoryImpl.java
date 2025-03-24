package com.devsu.res.movement_service.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.MovementEntity;
import com.devsu.res.movement_service.adapter.outbound.persistence.repository.MovementJpaRepository;
import com.devsu.res.movement_service.domain.model.Movement;
import com.devsu.res.movement_service.domain.port.outbound.MovementRepositoryPort;

@Repository
public class MovementRepositoryImpl implements MovementRepositoryPort {

    private final MovementJpaRepository movementJpaRepository;
    private final ModelMapper modelMapper;

    public MovementRepositoryImpl(MovementJpaRepository movementJpaRepository, ModelMapper modelMapper) {
        this.movementJpaRepository = movementJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Movement save(Movement movimiento) {
        MovementEntity entity = modelMapper.map(movimiento, MovementEntity.class);
        MovementEntity savedEntity = movementJpaRepository.save(entity);
        return modelMapper.map(savedEntity, Movement.class);
    }

    @Override
    public Optional<Movement> findById(UUID id) {
        return movementJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Movement.class));
    }

    @Override
    public List<Movement> findByAccountId(UUID cuentaId) {
        return movementJpaRepository.findByAccountId(cuentaId).stream()
                .map(entity -> modelMapper.map(entity, Movement.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        movementJpaRepository.deleteById(id);
    }
}