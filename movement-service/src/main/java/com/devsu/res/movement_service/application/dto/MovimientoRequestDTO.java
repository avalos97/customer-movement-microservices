package com.devsu.res.movement_service.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.devsu.res.movement_service.common.validation.NotZero;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRequestDTO {
    private UUID cuentaId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    private String tipoMovimiento;
    @NotZero(message = "no puede ser cero")
    private BigDecimal valor;
    private Boolean estado;
}