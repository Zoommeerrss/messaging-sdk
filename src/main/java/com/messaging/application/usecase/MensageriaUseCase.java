package com.messaging.application.usecase;

import com.messaging.domain.IEmailService;
import com.messaging.domain.IWhatsappService;

public class MensageriaUseCase {
    private final IEmailService emailService;
    private final IWhatsappService whatsappService;

    public MensageriaUseCase(IEmailService emailService, IWhatsappService whatsappService) {
        this.emailService = emailService;
        this.whatsappService = whatsappService;
    }

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        emailService.enviarEmail(destinatario, assunto, corpo);
    }

    public void enviarWhatsapp(String numeroDestino, String mensagem) {
        whatsappService.enviarMensagem(numeroDestino, mensagem);
    }
}

