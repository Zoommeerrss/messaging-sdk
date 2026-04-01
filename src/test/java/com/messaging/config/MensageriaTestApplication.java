package com.messaging.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MensageriaAutoConfiguration.class, MensageriaManagementConfiguration.class})
public class MensageriaTestApplication {
    // Não precisa de main, apenas serve como configuração
}
