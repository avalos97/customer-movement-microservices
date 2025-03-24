package com.devsu.res.customer_service.configuration.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /**
     * @return elemento de configuracion de model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return customMapping(mapper);
    }

    private ModelMapper customMapping(ModelMapper modelMapper) {
        return modelMapper;
    } 
}
