package com.devsu.res.customer_service.domain.port.outbound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devsu.res.customer_service.domain.model.Client;

/**
 * Se define el contrato de persistencia para Cliente
 */
public interface ClientRepositoryPort {

    /**
     * Guarda o actualiza un Cliente.
     * @param cliente Cliente a guardar.
     * @return Cliente guardado.
     */
    Client save(Client cliente);

    /**
     * Busca un Cliente por su identificador.
     * @param id Identificador del Cliente.
     * @return Optional con el Cliente encontrado.
     */
    Optional<Client> findById(UUID id);

    /**
     * Lista todos los Clientes.
     * @return Lista de Clientes.
     */
    List<Client> findAll();

    /**
     * Elimina un Cliente por su identificador.
     * @param id Identificador del Cliente a eliminar.
     */
    void deleteById(UUID id);
}
