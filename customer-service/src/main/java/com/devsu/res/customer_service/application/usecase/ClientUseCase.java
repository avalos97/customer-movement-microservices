package com.devsu.res.customer_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientResponseDTO;

public interface ClientUseCase {

    ClientResponseDTO createClient(ClientRequestDTO requestDTO);

    ClientResponseDTO createClientWithAccount(ClientWhitAccountRequestDTO requestDTO);

    ClientResponseDTO updateClient(UUID id, ClientRequestDTO requestDTO);

    ClientResponseDTO getClientById(UUID id);

    void deleteClient(UUID id);

    List<ClientResponseDTO> getAllClients();
}