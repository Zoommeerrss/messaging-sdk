package com.messaging.domain;

public interface IWhatsappService {
    void enviarMensagem(String numeroDestino, String mensagem);
}
