package com.devsu.res.movement_service.domain.port.outbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devsu.res.movement_service.domain.model.Cuenta;

/**
 * Define las operaciones de persistencia para cuentas.
 */
public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);
    Optional<Cuenta> findById(UUID id);
    List<Cuenta> findAll();
    void deleteById(UUID id);
    List<Cuenta> findAccountsWithMovementsByClientAndDate(UUID clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
