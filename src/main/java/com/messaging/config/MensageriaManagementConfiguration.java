package com.messaging.config;

import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ManagementContextConfiguration
public class MensageriaManagementConfiguration {

    @Bean
    public MensageriaHealthIndicator mensageriaHealthIndicator() {
        return new MensageriaHealthIndicator();
    }
}
