package com.messaging.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;

public class MensageriaHealthIndicator implements HealthIndicator {

    @Override
    public org.springframework.boot.health.contributor.Health health() {
        // Aqui poderia haver lógica real de verificação
        return Health.up().withDetail("mensageria", "Serviços de mensageria ativos").build();
    }
}

