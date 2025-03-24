package com.devsu.res.customer_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClienteRequestDTO;
import com.devsu.res.customer_service.application.dto.ClienteResponseDTO;

public interface ClienteUseCase {

    ClienteResponseDTO createCliente(ClienteRequestDTO requestDTO);

    ClienteResponseDTO createClientWithAccount(ClientWhitAccountRequestDTO requestDTO);

    ClienteResponseDTO updateCliente(UUID id, ClienteRequestDTO requestDTO);

    ClienteResponseDTO getClienteById(UUID id);

    void deleteCliente(UUID id);

    List<ClienteResponseDTO> getAllClientes();
}