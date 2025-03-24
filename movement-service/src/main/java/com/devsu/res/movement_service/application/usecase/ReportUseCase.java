package com.devsu.res.movement_service.application.usecase;

import java.time.LocalDate;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.AccountStateReportDTO;

public interface ReportUseCase {
    AccountStateReportDTO generateAccountStateReport(LocalDate fechaInicio, LocalDate fechaFin, UUID clienteId);
}