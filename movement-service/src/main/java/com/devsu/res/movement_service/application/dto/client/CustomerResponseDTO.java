package com.devsu.res.movement_service.application.dto.client;

import lombok.Data;
import java.util.UUID;

@Data
public class CustomerResponseDTO {
    private UUID id;
    private String nombre;
}