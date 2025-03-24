package com.devsu.res.movement_service.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.AccountEntity;
import com.devsu.res.movement_service.adapter.outbound.persistence.repository.AccountJpaRepository;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.model.Account;
import com.devsu.res.movement_service.domain.port.outbound.AccountRepositoryPort;

@Repository
public class AccountRepositoryImpl extends BaseMapper<AccountEntity, Account> implements AccountRepositoryPort {

    private final AccountJpaRepository accountJpaRepository;

    public AccountRepositoryImpl(AccountJpaRepository accountJpaRepository, ModelMapper modelMapper) {
        this.accountJpaRepository = accountJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Account save(Account cuenta) {
        AccountEntity entity = this.dtoToEntity(cuenta);
        AccountEntity savedEntity = accountJpaRepository.save(entity);
        return this.entityToDto(savedEntity);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountJpaRepository.findById(id)
                .map(entity -> this.entityToDto(entity));
    }

    @Override
    public List<Account> findAll() {
        var cuentas = accountJpaRepository.findAll();
        return this.entityListToDtoList(cuentas);
    }

    @Override
    public void deleteById(UUID id) {
        accountJpaRepository.deleteById(id);
    }

    @Override
    public List<Account> findAccountsWithMovementsByClientAndDate(UUID clienteId, LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        List<AccountEntity> entities = accountJpaRepository.findAccountsWithMovementsByClientAndDate(clienteId,
                fechaInicio, fechaFin);
        return this.entityListToDtoList(entities);
    }

    @Override
    public List<Account> findByClientId(UUID clienteId) {
        List<AccountEntity> entities = accountJpaRepository.findByClientIdWithMovements(clienteId);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Account.class))
                .collect(Collectors.toList());
    }

    @Override
    protected Class<AccountEntity> getEntityClass() {
        return AccountEntity.class;
    }

    @Override
    protected Class<Account> getDtoClass() {
        return Account.class;
    }

}