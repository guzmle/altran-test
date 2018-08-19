package com.altran.searcher.dataaccess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Clase para la configuracion del acceso a dato
 * Created by guzmle on 18/8/18.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class DataAccessConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
