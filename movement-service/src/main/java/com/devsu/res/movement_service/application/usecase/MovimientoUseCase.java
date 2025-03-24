package com.devsu.res.movement_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.MovimientoResponseDTO;

public interface MovimientoUseCase {
    MovimientoResponseDTO getMovimientoById(UUID id);
    List<MovimientoResponseDTO> getMovimientosByCuenta(UUID cuentaId);
}