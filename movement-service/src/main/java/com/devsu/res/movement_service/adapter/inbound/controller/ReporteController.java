package com.devsu.res.movement_service.adapter.inbound.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.res.movement_service.application.dto.ReporteEstadoCuentaDTO;
import com.devsu.res.movement_service.application.usecase.ReporteUseCase;
import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.response.ApiResponse;
import com.devsu.res.movement_service.common.util.DateUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteUseCase reporteUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<ReporteEstadoCuentaDTO>> generarReporte(@RequestParam("fechaInicio") String fechaInicio,
                                                                              @RequestParam("fechaFin") String fechaFin,
                                                                              @RequestParam("clienteId") UUID clienteId) {
        LocalDate inicio = DateUtil.parse(fechaInicio).toLocalDate();
        LocalDate fin = DateUtil.parse(fechaFin).toLocalDate();
        ReporteEstadoCuentaDTO reporte = reporteUseCase.generarReporteEstadoCuenta(inicio, fin, clienteId);
        ApiResponse<ReporteEstadoCuentaDTO> response = new ApiResponse<>(reporte, "Reporte generado exitosamente", ApiStatus.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}
