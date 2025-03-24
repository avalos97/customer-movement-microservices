package com.devsu.res.customer_service.adapter.inbound.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientResponseDTO;
import com.devsu.res.customer_service.application.usecase.ClientUseCase;
import com.devsu.res.customer_service.common.constant.ApiStatus;
import com.devsu.res.customer_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientUseCase clientUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDTO>> createClient(@RequestBody @Valid ClientRequestDTO requestDTO) {
        ClientResponseDTO responseDTO = clientUseCase.createClient(requestDTO);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente creado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/whit-account")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> createClientWithAccount(
            @RequestBody @Valid ClientWhitAccountRequestDTO requestDTO) {
        ClientResponseDTO responseDTO = clientUseCase.createClientWithAccount(requestDTO);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente creado y cuenta emitida", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> updateClient(@PathVariable UUID id, @RequestBody  @Valid  ClientRequestDTO requestDTO) {
        ClientResponseDTO responseDTO = clientUseCase.updateClient(id, requestDTO);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente actualizado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getClientById(@PathVariable UUID id) {
        ClientResponseDTO responseDTO = clientUseCase.getClientById(id);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente obtenido exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDTO>>> getAllClients() {
        List<ClientResponseDTO> responseDTOs = clientUseCase.getAllClients();
        ApiResponse<List<ClientResponseDTO>> response = new ApiResponse<>(responseDTOs, "Clientes listados exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable UUID id) {
        clientUseCase.deleteClient(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Cliente eliminado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}