package com.messaging.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Health;

import static org.assertj.core.api.Assertions.assertThat;

class MensageriaHealthIndicatorTest {

    @Test
    void deveRetornarHealthUp() {
        MensageriaHealthIndicator indicator = new MensageriaHealthIndicator();
        Health health = indicator.health();

        assertThat(health.getStatus().getCode()).isEqualTo("UP");
        assertThat(health.getDetails()).containsEntry("mensageria", "Serviços de mensageria ativos");
    }
}

