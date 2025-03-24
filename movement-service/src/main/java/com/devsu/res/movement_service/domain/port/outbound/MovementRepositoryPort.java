package com.devsu.res.movement_service.domain.port.outbound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devsu.res.movement_service.domain.model.Movement;

/**
 * Define las operaciones de persistencia para movimientos.
 */
public interface MovementRepositoryPort {
    Movement save(Movement movimiento);
    Optional<Movement> findById(UUID id);
    List<Movement> findByAccountId(UUID cuentaId);
    void deleteById(UUID id);
}