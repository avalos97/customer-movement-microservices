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

import com.devsu.res.movement_service.application.dto.CuentaRequestDTO;
import com.devsu.res.movement_service.application.dto.CuentaResponseDTO;
import com.devsu.res.movement_service.application.dto.MovimientoRequestDTO;
import com.devsu.res.movement_service.application.usecase.CuentaUseCase;
import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaUseCase cuentaUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<CuentaResponseDTO>> createCuenta(@RequestBody @Valid CuentaRequestDTO requestDTO) {
        CuentaResponseDTO responseDTO = cuentaUseCase.createCuenta(requestDTO);
        ApiResponse<CuentaResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta creada exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/movimiento")
    public ResponseEntity<ApiResponse<CuentaResponseDTO>> registrarMovimiento(
            @PathVariable UUID id,
            @RequestBody @Valid MovimientoRequestDTO requestDTO) {
                CuentaResponseDTO responseDTO = cuentaUseCase.registrarMovimiento(id, requestDTO);
        ApiResponse<CuentaResponseDTO> response = new ApiResponse<>(responseDTO,
                "Movimiento registrado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CuentaResponseDTO>> updateCuenta(@PathVariable UUID id,
            @RequestBody @Valid CuentaRequestDTO requestDTO) {
        CuentaResponseDTO responseDTO = cuentaUseCase.updateCuenta(id, requestDTO);
        ApiResponse<CuentaResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta actualizada exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CuentaResponseDTO>> getCuentaById(@PathVariable UUID id) {
        CuentaResponseDTO responseDTO = cuentaUseCase.getCuentaById(id);
        ApiResponse<CuentaResponseDTO> response = new ApiResponse<>(responseDTO, "Cuenta obtenida exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CuentaResponseDTO>>> getAllCuentas() {
        List<CuentaResponseDTO> responseDTOs = cuentaUseCase.getAllCuentas();
        ApiResponse<List<CuentaResponseDTO>> response = new ApiResponse<>(responseDTOs, "Cuentas listadas exitosamente",
                ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCuenta(@PathVariable UUID id) {
        cuentaUseCase.deleteCuenta(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Cuenta eliminada exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}