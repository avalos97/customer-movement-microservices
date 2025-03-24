package com.devsu.res.movement_service.adapter.inbound.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.res.movement_service.application.dto.MovimientoResponseDTO;
import com.devsu.res.movement_service.application.usecase.MovimientoUseCase;
import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoUseCase movimientoUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovimientoResponseDTO>> getMovimientoById(@PathVariable UUID id) {
        MovimientoResponseDTO responseDTO = movimientoUseCase.getMovimientoById(id);
        ApiResponse<MovimientoResponseDTO> response = new ApiResponse<>(responseDTO, "Movimiento obtenido exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovimientoResponseDTO>>> getMovimientosByCuenta(@RequestParam UUID cuentaId) {
        List<MovimientoResponseDTO> responseDTOs = movimientoUseCase.getMovimientosByCuenta(cuentaId);
        ApiResponse<List<MovimientoResponseDTO>> response = new ApiResponse<>(responseDTOs, "Movimientos listados exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}