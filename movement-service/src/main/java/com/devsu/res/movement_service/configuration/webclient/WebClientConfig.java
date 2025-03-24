package com.devsu.res.movement_service.configuration.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder, 
                               @Value("${api.rest.customer-service.url}") String customerServiceUrl) {
        return builder.baseUrl(customerServiceUrl).build();
    }
}