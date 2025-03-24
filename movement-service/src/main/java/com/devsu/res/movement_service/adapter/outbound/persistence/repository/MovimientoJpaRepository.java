package com.devsu.res.movement_service.adapter.outbound.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.res.movement_service.adapter.outbound.persistence.entity.MovimientoEntity;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, UUID> {
    List<MovimientoEntity> findByCuentaId(UUID cuentaId);
}