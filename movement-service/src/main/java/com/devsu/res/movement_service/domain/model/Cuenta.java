package com.devsu.res.movement_service.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private UUID id;
    private Long numeroCuenta;       
    private UUID clienteId;         
    private String tipoCuenta;       
    private BigDecimal saldoInicial;
    private Boolean estado;
    private List<Movimiento> movimientos; 
}