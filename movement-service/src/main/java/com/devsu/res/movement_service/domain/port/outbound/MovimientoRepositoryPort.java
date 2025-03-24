package com.devsu.res.movement_service.domain.port.outbound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devsu.res.movement_service.domain.model.Movimiento;

/**
 * Define las operaciones de persistencia para movimientos.
 */
public interface MovimientoRepositoryPort {
    Movimiento save(Movimiento movimiento);
    Optional<Movimiento> findById(UUID id);
    List<Movimiento> findByCuentaId(UUID cuentaId);
    void deleteById(UUID id);
}