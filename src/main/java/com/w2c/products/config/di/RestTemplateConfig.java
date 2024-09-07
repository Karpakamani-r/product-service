package com.w2c.products.config.di;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate provideRestTemplate(){
        return new RestTemplate();
    }
}
