package com.devsu.res.customer_service.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devsu.res.customer_service.adapter.outbound.messaging.ClienteKafkaProducer;
import com.devsu.res.customer_service.application.dto.AccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClienteRequestDTO;
import com.devsu.res.customer_service.application.dto.ClienteResponseDTO;
import com.devsu.res.customer_service.application.usecase.ClienteUseCase;
import com.devsu.res.customer_service.common.constant.ErrorCode;
import com.devsu.res.customer_service.common.mapper.BaseMapper;
import com.devsu.res.customer_service.domain.exception.ClienteNotFoundException;
import com.devsu.res.customer_service.domain.model.Cliente;
import com.devsu.res.customer_service.domain.port.outbound.ClienteRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl extends BaseMapper<Cliente, ClienteResponseDTO> implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ClienteKafkaProducer clienteKafkaProducer;

    @Override
    public ClienteResponseDTO createCliente(ClienteRequestDTO requestDTO) {
        Cliente cliente = modelMapper.map(requestDTO, Cliente.class);
        cliente.setEstado(true);
        Cliente created = clienteRepositoryPort.save(cliente);
        ClienteResponseDTO responseDTO = this.entityToDto(created);
        return responseDTO;
    }

    @Override
    public ClienteResponseDTO createClientWithAccount(ClientWhitAccountRequestDTO requestDTO) {
        ClienteRequestDTO clienteRequest = new ClienteRequestDTO(
                requestDTO.getNombre(),
                requestDTO.getGenero(),
                requestDTO.getEdad(),
                requestDTO.getIdentificacion(),
                requestDTO.getDireccion(),
                requestDTO.getTelefono(),
                requestDTO.getContrasena(),
                requestDTO.getEstado());
        ClienteResponseDTO clienteResponse = createCliente(clienteRequest);
        AccountRequestDTO cuentaRequest = requestDTO.getAccount();
        cuentaRequest.setClienteId(clienteResponse.getId());
        cuentaRequest.setOperation("CREATE");
        clienteKafkaProducer.sendAccountMessage(cuentaRequest);
        return clienteResponse;
    }

    @Override
    public ClienteResponseDTO updateCliente(UUID id, ClienteRequestDTO requestDTO) {
        Cliente existing = clienteRepositoryPort.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cliente no encontrado con id: " + id));

        Boolean previousState = existing.getEstado();
        modelMapper.map(requestDTO, existing);
        Cliente updated = clienteRepositoryPort.save(existing);
        ClienteResponseDTO clienteResponse = this.entityToDto(updated);
        if (!previousState.equals(updated.getEstado())) {
            AccountRequestDTO cuentaRequest = new AccountRequestDTO();
            cuentaRequest.setClienteId(id);
            cuentaRequest.setOperation("UPDATE_STATUS");
            cuentaRequest.setSaldoInicial(BigDecimal.ZERO);
            cuentaRequest.setEstado(updated.getEstado());
            clienteKafkaProducer.sendAccountMessage(cuentaRequest);
        }

        return clienteResponse;
    }

    @Override
    public ClienteResponseDTO getClienteById(UUID id) {
        Cliente cliente = clienteRepositoryPort.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cliente no encontrado con id: " + id));
        return this.entityToDto(cliente);
    }

    @Override
    public void deleteCliente(UUID id) {
        Cliente cliente = clienteRepositoryPort.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, "Cliente no encontrado con id: " + id));
        clienteRepositoryPort.deleteById(cliente.getId());
        AccountRequestDTO accountDeletionRequest = new AccountRequestDTO();
        accountDeletionRequest.setClienteId(cliente.getId());
        accountDeletionRequest.setOperation("DELETE");
        accountDeletionRequest.setSaldoInicial(BigDecimal.ZERO);
        clienteKafkaProducer.sendAccountMessage(accountDeletionRequest);
    }

    @Override
    public List<ClienteResponseDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepositoryPort.findAll();
        return this.entityListToDtoList(clientes);
    }

    @Override
    protected Class<Cliente> getEntityClass() {
        return Cliente.class;
    }

    @Override
    protected Class<ClienteResponseDTO> getDtoClass() {
        return ClienteResponseDTO.class;
    }
}