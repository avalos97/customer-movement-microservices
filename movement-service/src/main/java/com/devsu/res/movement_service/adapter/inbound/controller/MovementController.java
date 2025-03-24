package com.devsu.res.movement_service.adapter.inbound.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.res.movement_service.application.dto.MovementResponseDTO;
import com.devsu.res.movement_service.application.usecase.MovementUseCase;
import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final MovementUseCase movementUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovementResponseDTO>> getMovementById(@PathVariable UUID id) {
        MovementResponseDTO responseDTO = movementUseCase.getMovementById(id);
        ApiResponse<MovementResponseDTO> response = new ApiResponse<>(responseDTO, "Movimiento obtenido exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovementResponseDTO>>> getMovementsByAccount(@RequestParam UUID cuentaId) {
        List<MovementResponseDTO> responseDTOs = movementUseCase.getMovementsByAccount(cuentaId);
        ApiResponse<List<MovementResponseDTO>> response = new ApiResponse<>(responseDTOs, "Movimientos listados exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}