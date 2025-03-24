package com.devsu.res.customer_service.adapter.outbound.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.res.customer_service.adapter.outbound.persistence.entity.ClientEntity;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID> {
}