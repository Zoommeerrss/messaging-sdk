package com.messaging.infrastructure;

import com.messaging.config.MensageriaProperties;
import com.messaging.domain.IWhatsappService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class DefaultWhatsappService implements IWhatsappService {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String token;

    public DefaultWhatsappService(MensageriaProperties properties) {
        this(properties, new RestTemplate());
    }

    public DefaultWhatsappService(MensageriaProperties properties, RestTemplate restTemplate) {
        this.apiUrl = properties.getWhatsapp().getApiUrl();
        this.token = properties.getWhatsapp().getToken();
        this.restTemplate = restTemplate;
    }

    @Override
    public void enviarMensagem(String numeroDestino, String mensagem) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", numeroDestino,
                "type", "text",
                "text", Map.of("body", mensagem)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(apiUrl, request, String.class);
    }
}
