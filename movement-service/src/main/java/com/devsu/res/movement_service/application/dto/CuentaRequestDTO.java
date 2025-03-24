package com.devsu.res.movement_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {
    @NotNull(message = "no debe ser nulo")
    @Digits(integer = 6, fraction = 0, message = "El número de cuenta debe tener 6 dígitos")
    private Long numeroCuenta;
    private UUID clienteId;
    @NotBlank(message = "no debe ser nulo")
    @Pattern(regexp = "^(AHORRO|CORRIENTE)$", message = "debe ser AHORRO o CORRIENTE")
    private String tipoCuenta;
    @NotNull(message = "no debe ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "no puede ser negativo")
    private BigDecimal saldoInicial;
    private Boolean estado;

    private String operation; 
}