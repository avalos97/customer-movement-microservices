package com.devsu.res.movement_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.CuentaRequestDTO;
import com.devsu.res.movement_service.application.dto.CuentaResponseDTO;
import com.devsu.res.movement_service.application.dto.MovimientoRequestDTO;

public interface CuentaUseCase {
    CuentaResponseDTO createCuenta(CuentaRequestDTO requestDTO);
    CuentaResponseDTO updateCuenta(UUID id, CuentaRequestDTO requestDTO);
    CuentaResponseDTO getCuentaById(UUID id);
    void deleteCuenta(UUID id);
    List<CuentaResponseDTO> getAllCuentas();
    CuentaResponseDTO registrarMovimiento(UUID cuentaId, MovimientoRequestDTO requestDTO);

}