package com.devsu.res.customer_service.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.customer_service.adapter.outbound.persistence.entity.ClienteEntity;
import com.devsu.res.customer_service.adapter.outbound.persistence.repository.ClienteJpaRepository;
import com.devsu.res.customer_service.domain.model.Cliente;
import com.devsu.res.customer_service.domain.port.outbound.ClienteRepositoryPort;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ModelMapper modelMapper;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository, ModelMapper modelMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = modelMapper.map(cliente, ClienteEntity.class);
        ClienteEntity savedEntity = clienteJpaRepository.save(entity);
        return modelMapper.map(savedEntity, Cliente.class);
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        Optional<ClienteEntity> entityOpt = clienteJpaRepository.findById(id);
        return entityOpt.map(entity -> modelMapper.map(entity, Cliente.class));
    }

    @Override
    public List<Cliente> findAll() {
        List<ClienteEntity> entities = clienteJpaRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Cliente.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        clienteJpaRepository.deleteById(id);
    }
}