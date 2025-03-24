package com.devsu.res.movement_service.adapter.inbound.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devsu.res.movement_service.application.dto.AccountRequestDTO;
import com.devsu.res.movement_service.application.usecase.AccountUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final AccountUseCase accountUseCase; // Se asume que esta interfaz tiene métodos para crear y eliminar cuentas.

    @KafkaListener(topics = "${kafka.topic.client}", groupId = "${kafka.group.id}")
    public void consume(String message) {
        log.info("Mensaje recibido en CuentaKafkaConsumer: {}", message);
        try {
            AccountRequestDTO accountRequestDTO = objectMapper.readValue(message, AccountRequestDTO.class);
            Runnable action = switch (accountRequestDTO.getOperation().toUpperCase()) {
                case "DELETE" -> () -> accountUseCase.deleteAccountByClientId(accountRequestDTO.getClienteId());
                case "UPDATE_STATUS" -> () -> accountUseCase.updateAccountStatusByClientId(accountRequestDTO.getClienteId(), accountRequestDTO.getEstado());
                default -> () -> accountUseCase.createAccount(accountRequestDTO);
            };
            action.run();
        } catch (Exception e) {
            log.error("Error al procesar el mensaje de Kafka", e);
        }
    }
}
