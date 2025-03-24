package com.devsu.res.customer_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envoltorio genérico para respuestas de la API.
 * Permite mantener un estándar para respuestas exitosas y de error.
 *
 * @param <T> Tipo de dato que se retorna en la propiedad 'item'
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    /**
     * Contiene el objeto, lista o página a devolver.
     * En caso de error, este campo contendrá un objeto ErrorDetails.
     */
    private T item;
    
    /**
     * Mensaje descriptivo de la transacción u operación.
     */
    private String message;
    
    /**
     * Estado de la respuesta (por ejemplo, "SUCCESS" o "ERROR").
     */
    private String status;
}