package com.messaging.domain;

public interface IEmailService {
    void enviarEmail(String destinatario, String assunto, String corpo);
}
