package com.messaging.config;

import com.messaging.domain.IEmailService;
import com.messaging.domain.IWhatsappService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class MensageriaAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MensageriaAutoConfiguration.class))
            .withBean(JavaMailSender.class, () -> Mockito.mock(JavaMailSender.class))
            .withBean(MensageriaProperties.class, MensageriaProperties::new);

    @Test
    void deveCriarBeanEmailServiceQuandoEnabled() {
        contextRunner
                .withPropertyValues("mensageria.email.enabled=true")
                .run(context -> assertThat(context).hasSingleBean(IEmailService.class));
    }

    @Test
    void naoDeveCriarBeanEmailServiceQuandoDisabled() {
        contextRunner
                .withPropertyValues("mensageria.email.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(IEmailService.class));
    }

    @Test
    void deveCriarBeanWhatsappServiceQuandoEnabled() {
        contextRunner
                .withPropertyValues("mensageria.whatsapp.enabled=true")
                .run(context -> assertThat(context).hasSingleBean(IWhatsappService.class));
    }
}

