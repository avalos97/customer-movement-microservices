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
import com.devsu.res.customer_service.application.dto.ClienteRequestDTO;
import com.devsu.res.customer_service.application.dto.ClienteResponseDTO;
import com.devsu.res.customer_service.application.usecase.ClienteUseCase;
import com.devsu.res.customer_service.common.constant.ApiStatus;
import com.devsu.res.customer_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> createCliente(@RequestBody @Valid ClienteRequestDTO requestDTO) {
        ClienteResponseDTO responseDTO = clienteUseCase.createCliente(requestDTO);
        ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente creado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/whit-account")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> createClienteWithAccount(
            @RequestBody @Valid ClientWhitAccountRequestDTO requestDTO) {
        ClienteResponseDTO responseDTO = clienteUseCase.createClientWithAccount(requestDTO);
        ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente creado y cuenta emitida", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> updateCliente(@PathVariable UUID id, @RequestBody  @Valid  ClienteRequestDTO requestDTO) {
        ClienteResponseDTO responseDTO = clienteUseCase.updateCliente(id, requestDTO);
        ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente actualizado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> getClienteById(@PathVariable UUID id) {
        ClienteResponseDTO responseDTO = clienteUseCase.getClienteById(id);
        ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(responseDTO, "Cliente obtenido exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> getAllClientes() {
        List<ClienteResponseDTO> responseDTOs = clienteUseCase.getAllClientes();
        ApiResponse<List<ClienteResponseDTO>> response = new ApiResponse<>(responseDTOs, "Clientes listados exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCliente(@PathVariable UUID id) {
        clienteUseCase.deleteCliente(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Cliente eliminado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}