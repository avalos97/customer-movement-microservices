package com.devsu.res.customer_service.adapter.outbound.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "persona_id")
public class ClientEntity extends PersonEntity {

    private String contrasena;
    private Boolean estado;

    public ClientEntity(UUID id, String nombre, String genero, Integer edad, String identificacion,
            String direccion, String telefono, String contrasena, Boolean estado) {
        super(id, nombre, genero, edad, identificacion, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }
}