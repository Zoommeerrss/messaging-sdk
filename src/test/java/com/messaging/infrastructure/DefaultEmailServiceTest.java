package com.messaging.infrastructure;

import com.messaging.domain.IEmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.verify;

class DefaultEmailServiceTest {

    @Test
    void deveEnviarEmail() {
        JavaMailSender mailSender = Mockito.mock(JavaMailSender.class);
        IEmailService emailService = new DefaultEmailService(mailSender);

        emailService.enviarEmail("teste@dominio.com", "Assunto", "Corpo");

        verify(mailSender).send(Mockito.any(SimpleMailMessage.class));
    }
}
