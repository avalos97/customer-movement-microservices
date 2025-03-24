package com.devsu.res.movement_service.adapter.inbound.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devsu.res.movement_service.application.dto.CuentaRequestDTO;
import com.devsu.res.movement_service.application.usecase.CuentaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CuentaKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final CuentaUseCase cuentaUseCase; // Se asume que esta interfaz tiene métodos para crear y eliminar cuentas.

    @KafkaListener(topics = "${kafka.topic.client}", groupId = "${kafka.group.id}")
    public void consume(String message) {
        log.info("Mensaje recibido en CuentaKafkaConsumer: {}", message);
        try {
            CuentaRequestDTO cuentaRequestDTO = objectMapper.readValue(message, CuentaRequestDTO.class);
            Runnable action = switch (cuentaRequestDTO.getOperation().toUpperCase()) {
                case "DELETE" -> () -> cuentaUseCase.deleteCuentaByClienteId(cuentaRequestDTO.getClienteId());
                case "UPDATE_STATUS" -> () -> cuentaUseCase.updateCuentaStatusByClienteId(cuentaRequestDTO.getClienteId(), cuentaRequestDTO.getEstado());
                default -> () -> cuentaUseCase.createCuenta(cuentaRequestDTO);
            };
            action.run();
        } catch (Exception e) {
            log.error("Error al procesar el mensaje de Kafka", e);
        }
    }
}
