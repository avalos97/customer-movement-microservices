package com.devsu.res.customer_service.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {
    @NotBlank(message = "El nombre no debe ser nulo")
    private String nombre;

    @NotBlank(message = "El género no debe ser nulo")
    @Pattern(regexp = "^(MASCULINO|FEMENINO)$", message = "El género solo puede ser MASCULINO o FEMENINO")
    private String genero;

    @NotNull(message = "La edad no debe ser nula")
    @Min(value = 1, message = "La edad debe ser positiva")
    private Integer edad;

    @NotBlank(message = "La identificación no debe ser nula")
    private String identificacion;

    @NotBlank(message = "La dirección no debe ser nula")
    private String direccion;

    @NotBlank(message = "El teléfono no debe ser nulo")
    private String telefono;

    @NotBlank(message = "La contraseña no debe ser nula")
    private String contrasena;

    private Boolean estado;
}
