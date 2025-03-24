package com.devsu.res.movement_service.adapter.outbound.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devsu.res.movement_service.application.dto.client.CustomerResponseDTO;
import com.devsu.res.movement_service.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceClientImpl implements CustomerServiceClient {

    private final WebClient webClient;
    @Value("${api.rest.customer-service.client-path}")
    private String clientPath;

    @Override
    public CustomerResponseDTO getClientById(UUID id) {
        ApiResponse<CustomerResponseDTO> response = webClient.get()
                .uri(clientPath, id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<CustomerResponseDTO>>() {})
                .block();
        
        return response.getItem();
    }
}