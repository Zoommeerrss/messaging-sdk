package com.messaging.infrastructure;

import com.messaging.config.MensageriaProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

class DefaultWhatsappServiceTest {

    @Test
    void deveEnviarMensagemWhatsapp() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class)))
                .thenReturn(ResponseEntity.ok("ok"));

        MensageriaProperties props = new MensageriaProperties();
        MensageriaProperties.WhatsappProperties wprops = new MensageriaProperties.WhatsappProperties();
        wprops.setApiUrl("http://localhost:8080/mock");
        wprops.setToken("TOKEN_TESTE");
        props.setWhatsapp(wprops);

        DefaultWhatsappService service = new DefaultWhatsappService(props, restTemplate);

        // Aqui poderíamos usar WireMock para simular a API
        service.enviarMensagem("5511999999999", "Mensagem de teste");
        Mockito.verify(restTemplate).postForEntity(Mockito.eq("http://localhost:8080/mock"), Mockito.any(), Mockito.eq(String.class));
    }
}
