package com.devsu.res.movement_service.adapter.outbound.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.CuentaEntity;

@Repository
public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, UUID> {

       @Query("SELECT c FROM CuentaEntity c " +
                     "INNER JOIN FETCH c.movimientos m " +
                     "WHERE c.clienteId = :clienteId " +
                     "AND (m IS NULL OR (m.fecha BETWEEN :fechaInicio AND :fechaFin))")
       List<CuentaEntity> findCuentasWithMovimientosByClienteAndFecha(
                     @Param("clienteId") UUID clienteId,
                     @Param("fechaInicio") LocalDateTime fechaInicio,
                     @Param("fechaFin") LocalDateTime fechaFin);

       @Query("SELECT DISTINCT c FROM CuentaEntity c LEFT JOIN FETCH c.movimientos WHERE c.clienteId = :clienteId")
       List<CuentaEntity> findByClienteIdWithMovimientos(@Param("clienteId") UUID clienteId);

}