package com.devsu.res.movement_service.domain.port.outbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devsu.res.movement_service.domain.model.Account;

/**
 * Define las operaciones de persistencia para cuentas.
 */
public interface AccountRepositoryPort {
    Account save(Account cuenta);
    Optional<Account> findById(UUID id);
    List<Account> findAll();
    void deleteById(UUID id);
    List<Account> findAccountsWithMovementsByClientAndDate(UUID clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Account> findByClientId(UUID clienteId);

}
