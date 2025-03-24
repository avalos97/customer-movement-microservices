package com.devsu.res.movement_service.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.application.dto.AccountRequestDTO;
import com.devsu.res.movement_service.application.dto.AccountResponseDTO;
import com.devsu.res.movement_service.application.dto.MovementRequestDTO;
import com.devsu.res.movement_service.application.usecase.AccountUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.common.util.DateUtil;
import com.devsu.res.movement_service.domain.exception.AccountNotFoundException;
import com.devsu.res.movement_service.domain.exception.DomainException;
import com.devsu.res.movement_service.domain.exception.BalanceNotAvailableException;
import com.devsu.res.movement_service.domain.model.Account;
import com.devsu.res.movement_service.domain.model.Movement;
import com.devsu.res.movement_service.domain.port.outbound.AccountRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseMapper<Account, AccountResponseDTO> implements AccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO) {
        modelMapper.typeMap(AccountRequestDTO.class, Account.class)
                .addMappings(mapper -> mapper.skip(Account::setId));
        Account cuenta = modelMapper.map(requestDTO, Account.class);
        cuenta.setEstado(true);
        Account created = accountRepositoryPort.save(cuenta);
        return this.entityToDto(created);
    }

    @Override
    public AccountResponseDTO updateAccount(UUID id, AccountRequestDTO requestDTO) {
        Account existing = accountRepositoryPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + id));
        if (!existing.getSaldoInicial().equals(requestDTO.getSaldoInicial())) {
            throw new DomainException(
                    "No se puede modificar el saldo inicial de una cuenta, realice un movimiento",ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
        modelMapper.map(requestDTO, existing);
        Account updated = accountRepositoryPort.save(existing);
        return this.entityToDto(updated);
    }

    @Override
    public AccountResponseDTO getAccountById(UUID id) {
        Account cuenta = accountRepositoryPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + id));
        return this.entityToDto(cuenta);
    }

    @Override
    public void deleteAccount(UUID id) {
        accountRepositoryPort.deleteById(id);
    }

    @Override
    public List<AccountResponseDTO> getAllAccount() {
        List<Account> cuentas = accountRepositoryPort.findAll();
        return this.entityListToDtoList(cuentas);
    }

    @Override
    public AccountResponseDTO registerMovement(UUID accountId, MovementRequestDTO requestDTO) {
        Account account = accountRepositoryPort.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + accountId));
        requestDTO.setCuentaId(accountId);
        if (!account.getEstado()) {
            throw new IllegalStateException("No se puede operar sobre una cuenta inactiva");
        }

        String movementType = requestDTO.getValor().compareTo(BigDecimal.ZERO) > 0 ? "DEPOSITO" : "RETIRO";
        requestDTO.setTipoMovimiento(movementType);

        BigDecimal currentBalance = (account.getMovimientos() != null && !account.getMovimientos().isEmpty())
                ? account.getMovimientos().stream()
                        .filter(mov -> Boolean.TRUE.equals(mov.getEstado()))
                        .max(Comparator.comparing(Movement::getFecha))
                        .map(Movement::getSaldo)
                        .orElse(account.getSaldoInicial())
                : account.getSaldoInicial();

        Map<String, Function<BigDecimal, BigDecimal>> operations = Map.of(
                "RETIRO", valor -> {
                    var ammountPositive = valor.abs();
                    if (currentBalance.compareTo(ammountPositive) < 0) {
                        throw new BalanceNotAvailableException(ErrorCode.BALANCE_NOT_AVAILABLE,
                                "Saldo no disponible para retiro");
                    }
                    return currentBalance.subtract(ammountPositive);
                },
                "DEPOSITO", valor -> currentBalance.add(valor));

        Function<BigDecimal, BigDecimal> operation = operations.get(requestDTO.getTipoMovimiento().toUpperCase());
        if (operation == null) {
            throw new IllegalArgumentException("Tipo de movimiento no válido: " + requestDTO.getTipoMovimiento());
        }

        BigDecimal newBalance = operation.apply(requestDTO.getValor());

        modelMapper.typeMap(MovementRequestDTO.class, Movement.class)
                .addMappings(mapper -> mapper.skip(Movement::setId));
        Movement movement = modelMapper.map(requestDTO, Movement.class);
        movement.setSaldo(newBalance);
        movement.setEstado(true);
        movement.setFecha(DateUtil.now());

        Optional.ofNullable(account.getMovimientos())
                .orElseGet(ArrayList::new)
                .add(movement);

        Account updatedAccount = accountRepositoryPort.save(account);

        return this.entityToDto(updatedAccount);
    }

    @Override
    public void deleteAccountByClientId(UUID clientId) {
        accountRepositoryPort.findByClientId(clientId)
                .forEach(account -> accountRepositoryPort.deleteById(account.getId()));
    }

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    protected Class<AccountResponseDTO> getDtoClass() {
        return AccountResponseDTO.class;
    }

    @Override
    public void updateAccountStatusByClientId(UUID id, Boolean status) {
        List<Account> accounts = accountRepositoryPort.findByClientId(id);
        accounts.forEach(account -> {
            account.setEstado(status);
            accountRepositoryPort.save(account);
        });
    }
}
