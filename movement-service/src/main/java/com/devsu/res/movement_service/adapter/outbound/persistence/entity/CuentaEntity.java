package com.devsu.res.movement_service.adapter.outbound.persistence.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cuenta", schema = "public", catalog = "devsu_db")
public class CuentaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private Long numeroCuenta;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovimientoEntity> movimientos;
}