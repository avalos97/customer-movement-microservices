package com.devsu.res.customer_service.adapter.outbound.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.devsu.res.customer_service.application.dto.AccountRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${kafka.topic.client}")
    private String clientTopic;

    public void sendAccountMessage(AccountRequestDTO accountRequestDTO) {
        try {
            String json = objectMapper.writeValueAsString(accountRequestDTO);
            kafkaTemplate.send(clientTopic, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando CuentaRequestDTO", e);
        }
    }
}