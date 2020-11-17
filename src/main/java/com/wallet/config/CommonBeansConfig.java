package com.wallet.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonBeansConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
