package com.devsu.res.movement_service.application.usecase;

import java.time.LocalDate;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.ReporteEstadoCuentaDTO;

public interface ReporteUseCase {
    ReporteEstadoCuentaDTO generarReporteEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, UUID clienteId);
}