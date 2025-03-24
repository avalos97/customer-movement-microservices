package com.devsu.res.customer_service.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.devsu.res.customer_service.adapter.outbound.persistence.entity.ClientEntity;
import com.devsu.res.customer_service.adapter.outbound.persistence.repository.ClientJpaRepository;
import com.devsu.res.customer_service.domain.model.Client;
import com.devsu.res.customer_service.domain.port.outbound.ClientRepositoryPort;

@Repository
public class ClientRepositoryImpl implements ClientRepositoryPort {

    private final ClientJpaRepository clientJpaRepository;
    private final ModelMapper modelMapper;

    public ClientRepositoryImpl(ClientJpaRepository clientJpaRepository, ModelMapper modelMapper) {
        this.clientJpaRepository = clientJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Client save(Client cliente) {
        ClientEntity entity = modelMapper.map(cliente, ClientEntity.class);
        ClientEntity savedEntity = clientJpaRepository.save(entity);
        return modelMapper.map(savedEntity, Client.class);
    }

    @Override
    public Optional<Client> findById(UUID id) {
        Optional<ClientEntity> entityOpt = clientJpaRepository.findById(id);
        return entityOpt.map(entity -> modelMapper.map(entity, Client.class));
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> entities = clientJpaRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Client.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        clientJpaRepository.deleteById(id);
    }
}