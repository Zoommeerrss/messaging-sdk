package com.messaging.config;

import com.messaging.domain.IEmailService;
import com.messaging.domain.IWhatsappService;
import com.messaging.infrastructure.DefaultEmailService;
import com.messaging.infrastructure.DefaultWhatsappService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MensageriaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IEmailService.class)
    @ConditionalOnProperty(prefix = "mensageria.email", name = "enabled", havingValue = "true", matchIfMissing = true)
    public IEmailService emailService(JavaMailSender mailSender) {
        return new DefaultEmailService(mailSender);
    }

    @Bean
    @ConditionalOnMissingBean(IWhatsappService.class)
    @ConditionalOnProperty(prefix = "mensageria.whatsapp", name = "enabled", havingValue = "true", matchIfMissing = true)
    public IWhatsappService whatsappService(MensageriaProperties properties) {
        return new DefaultWhatsappService(properties);
    }
}

