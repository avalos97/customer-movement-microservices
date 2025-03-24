package com.devsu.res.customer_service.adapter.inbound.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.devsu.res.customer_service.application.dto.AccountRequestDTO;
import com.devsu.res.customer_service.application.dto.ClientWhitAccountRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {"client-topic"})
@ActiveProfiles("test")
public class CustomerServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    // This method is used to override the kafka properties
    @DynamicPropertySource
    static void overrideKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.bootstrap-servers", () -> System.getProperty("spring.embedded.kafka.brokers"));
    }
    
    @Test
    public void testCreateClienteWithAccount() throws Exception {
        AccountRequestDTO cuentaRequest = new AccountRequestDTO();
        cuentaRequest.setNumeroCuenta(123456L);
        cuentaRequest.setTipoCuenta("AHORRO");
        cuentaRequest.setSaldoInicial(new BigDecimal("1000.00"));
        
        ClientWhitAccountRequestDTO requestDTO = new ClientWhitAccountRequestDTO();
        requestDTO.setNombre("Juan Pérez");
        requestDTO.setGenero("MASCULINO");
        requestDTO.setEdad(30);
        requestDTO.setIdentificacion("12345678");
        requestDTO.setDireccion("Calle Falsa 123");
        requestDTO.setTelefono("0999999999");
        requestDTO.setContrasena("secret");
        requestDTO.setEstado(true);
        requestDTO.setAccount(cuentaRequest);
        
     
        mockMvc.perform(post("/clientes/whit-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }
}
