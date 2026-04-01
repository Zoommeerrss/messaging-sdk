package com.messaging.application.usecase;

import com.messaging.domain.IEmailService;
import com.messaging.domain.IWhatsappService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MensageriaUseCaseTest {

    @Test
    void deveDelegarEnvioEmail() {
        IEmailService emailService = Mockito.mock(IEmailService.class);
        IWhatsappService whatsappService = Mockito.mock(IWhatsappService.class);

        MensageriaUseCase useCase = new MensageriaUseCase(emailService, whatsappService);

        useCase.enviarEmail("teste@dominio.com", "Assunto", "Corpo");

        Mockito.verify(emailService).enviarEmail("teste@dominio.com", "Assunto", "Corpo");
    }

    @Test
    void deveDelegarEnvioWhatsapp() {
        IEmailService emailService = Mockito.mock(IEmailService.class);
        IWhatsappService whatsappService = Mockito.mock(IWhatsappService.class);

        MensageriaUseCase useCase = new MensageriaUseCase(emailService, whatsappService);

        useCase.enviarWhatsapp("5511999999999", "Mensagem");

        Mockito.verify(whatsappService).enviarMensagem("5511999999999", "Mensagem");
    }
}
