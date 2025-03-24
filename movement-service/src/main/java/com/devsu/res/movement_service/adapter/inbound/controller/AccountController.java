package com.devsu.res.movement_service.adapter.inbound.controller;

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

import com.devsu.res.movement_service.application.dto.AccountRequestDTO;
import com.devsu.res.movement_service.application.dto.AccountResponseDTO;
import com.devsu.res.movement_service.application.dto.MovementRequestDTO;
import com.devsu.res.movement_service.application.usecase.AccountUseCase;
import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponseDTO>> createAccount(@RequestBody @Valid AccountRequestDTO requestDTO) {
        AccountResponseDTO responseDTO = accountUseCase.createAccount(requestDTO);
        ApiResponse<AccountResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta creada exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/movimiento")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> registerMovement(
            @PathVariable UUID id,
            @RequestBody @Valid MovementRequestDTO requestDTO) {
                AccountResponseDTO responseDTO = accountUseCase.registerMovement(id, requestDTO);
        ApiResponse<AccountResponseDTO> response = new ApiResponse<>(responseDTO,
                "Movimiento registrado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> updateAccount(@PathVariable UUID id,
            @RequestBody @Valid AccountRequestDTO requestDTO) {
        AccountResponseDTO responseDTO = accountUseCase.updateAccount(id, requestDTO);
        ApiResponse<AccountResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta actualizada exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountById(@PathVariable UUID id) {
        AccountResponseDTO responseDTO = accountUseCase.getAccountById(id);
        ApiResponse<AccountResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta obtenida exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getAllAccount() {
        List<AccountResponseDTO> responseDTOs = accountUseCase.getAllAccount();
        ApiResponse<List<AccountResponseDTO>> response = new ApiResponse<>(responseDTOs, "Cuentas listadas exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable UUID id) {
        accountUseCase.deleteAccount(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Cuenta eliminada exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}