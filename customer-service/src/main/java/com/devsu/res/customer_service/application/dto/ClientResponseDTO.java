package com.devsu.res.customer_service.application.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private UUID id;
    // Datos de Persona
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    
    // Datos específicos de Cliente
    private String contrasena;
    private Boolean estado;
}
