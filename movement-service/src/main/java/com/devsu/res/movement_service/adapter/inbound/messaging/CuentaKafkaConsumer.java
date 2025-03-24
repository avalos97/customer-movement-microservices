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
    private final CuentaUseCase cuentaUseCase;

    @KafkaListener(topics = "${kafka.topic.client}", groupId = "${kafka.group.id}")
    public void consume(String message) {
        log.info("Mensaje recibido en CuentaKafkaConsumer: {}", message);
        try {
            CuentaRequestDTO cuentaRequestDTO = objectMapper.readValue(message, CuentaRequestDTO.class);
            cuentaUseCase.createCuenta(cuentaRequestDTO);
        } catch (Exception e) {
            log.error("Error al procesar el mensaje de Kafka", e);
        }
    }
}