package com.devsu.res.movement_service.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEstadoCuentaDTO {
    private UUID clienteId;
    private String clienteNombre;
    private List<CuentaReporteDTO> cuentas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CuentaReporteDTO {
        private UUID cuentaId;
        private Long numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoInicial;
        private List<MovementReportDTO> movimientos;
    }
}