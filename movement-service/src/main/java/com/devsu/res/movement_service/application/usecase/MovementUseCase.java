package com.devsu.res.movement_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.MovementResponseDTO;

public interface MovementUseCase {
    MovementResponseDTO getMovementById(UUID id);
    List<MovementResponseDTO> getMovementsByAccount(UUID cuentaId);
}