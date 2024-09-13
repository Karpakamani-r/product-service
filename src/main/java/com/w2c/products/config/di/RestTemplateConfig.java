package com.w2c.products.config.di;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static com.w2c.products.util.Constants.BASE_URL;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate provideRestTemplate() {
        return new RestTemplateBuilder().rootUri(BASE_URL).build();
    }
}
