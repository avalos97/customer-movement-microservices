package com.devsu.res.movement_service.adapter.outbound.client;

import java.util.UUID;

import com.devsu.res.movement_service.application.dto.client.CustomerResponseDTO;


public interface CustomerServiceClient {
    CustomerResponseDTO getClienteById(UUID id);
}