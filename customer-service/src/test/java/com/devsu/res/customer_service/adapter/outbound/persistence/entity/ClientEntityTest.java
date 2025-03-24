package com.devsu.res.customer_service.adapter.outbound.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ClientEntityTest {

    @Test
    public void testClientEntityCreation() {
        UUID id = UUID.randomUUID();

        String nombre = "Juan Pérez";
        String genero = "Masculino";
        Integer edad = 30;
        String identificacion = "1234567890";
        String direccion = "Calle Falsa 123";
        String telefono = "0999999999";
        
        String contrasena = "secret";
        Boolean estado = true;
        
        ClientEntity cliente = new ClientEntity(
                id,
                nombre,
                genero,
                edad,
                identificacion,
                direccion,
                telefono,
                contrasena,
                estado
        );
        
        assertEquals(id, cliente.getId());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(genero, cliente.getGenero());
        assertEquals(edad, cliente.getEdad());
        assertEquals(identificacion, cliente.getIdentificacion());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(telefono, cliente.getTelefono());
        assertEquals(contrasena, cliente.getContrasena());
        assertTrue(cliente.getEstado());
    }
}