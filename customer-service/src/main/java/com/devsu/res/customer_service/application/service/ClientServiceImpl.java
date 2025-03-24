package com.devsu.res.customer_service.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devsu.res.customer_service.adapter.outbound.messaging.ClientKafkaProducer;
import com.devsu.res.customer_service.application.dto.AccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientResponseDTO;
import com.devsu.res.customer_service.application.usecase.ClientUseCase;
import com.devsu.res.customer_service.common.constant.ErrorCode;
import com.devsu.res.customer_service.common.mapper.BaseMapper;
import com.devsu.res.customer_service.domain.exception.ClientNotFoundException;
import com.devsu.res.customer_service.domain.model.Client;
import com.devsu.res.customer_service.domain.port.outbound.ClientRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends BaseMapper<Client, ClientResponseDTO> implements ClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientKafkaProducer clientKafkaProducer;

    @Override
    public ClientResponseDTO createClient(ClientRequestDTO requestDTO) {
        Client client = modelMapper.map(requestDTO, Client.class);
        client.setEstado(true);
        Client created = clientRepositoryPort.save(client);
        ClientResponseDTO responseDTO = this.entityToDto(created);
        return responseDTO;
    }

    @Override
    public ClientResponseDTO createClientWithAccount(ClientWhitAccountRequestDTO requestDTO) {
        ClientRequestDTO clientRequest = new ClientRequestDTO(
                requestDTO.getNombre(),
                requestDTO.getGenero(),
                requestDTO.getEdad(),
                requestDTO.getIdentificacion(),
                requestDTO.getDireccion(),
                requestDTO.getTelefono(),
                requestDTO.getContrasena(),
                requestDTO.getEstado());
        ClientResponseDTO clientResponse = createClient(clientRequest);
        AccountRequestDTO accountRequest = requestDTO.getAccount();
        accountRequest.setClienteId(clientResponse.getId());
        accountRequest.setOperation("CREATE");
        clientKafkaProducer.sendAccountMessage(accountRequest);
        return clientResponse;
    }

    @Override
    public ClientResponseDTO updateClient(UUID id, ClientRequestDTO requestDTO) {
        Client existing = clientRepositoryPort.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cliente no encontrado con id: " + id));

        Boolean previousState = existing.getEstado();
        modelMapper.map(requestDTO, existing);
        Client updated = clientRepositoryPort.save(existing);
        ClientResponseDTO clientResponse = this.entityToDto(updated);
        if (!previousState.equals(updated.getEstado())) {
            AccountRequestDTO accountRequest = new AccountRequestDTO();
            accountRequest.setClienteId(id);
            accountRequest.setOperation("UPDATE_STATUS");
            accountRequest.setSaldoInicial(BigDecimal.ZERO);
            accountRequest.setEstado(updated.getEstado());
            clientKafkaProducer.sendAccountMessage(accountRequest);
        }

        return clientResponse;
    }

    @Override
    public ClientResponseDTO getClientById(UUID id) {
        Client client = clientRepositoryPort.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cliente no encontrado con id: " + id));
        return this.entityToDto(client);
    }

    @Override
    public void deleteClient(UUID id) {
        Client client = clientRepositoryPort.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, "Cliente no encontrado con id: " + id));
        clientRepositoryPort.deleteById(client.getId());
        AccountRequestDTO accountDeletionRequest = new AccountRequestDTO();
        accountDeletionRequest.setClienteId(client.getId());
        accountDeletionRequest.setOperation("DELETE");
        accountDeletionRequest.setSaldoInicial(BigDecimal.ZERO);
        clientKafkaProducer.sendAccountMessage(accountDeletionRequest);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepositoryPort.findAll();
        return this.entityListToDtoList(clients);
    }

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }

    @Override
    protected Class<ClientResponseDTO> getDtoClass() {
        return ClientResponseDTO.class;
    }
}