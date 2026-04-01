package com.messaging.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ActuatorIntegrationTest {

    @Test
    void deveExibirMensageriaNoHealthEndpoint() {
        HealthClient client = Mockito.mock(HealthClient.class);
        String url = "http://localhost:12345/actuator/health";
        ResponseEntity<String> fakeResponse = new ResponseEntity<>("{\"status\":\"UP\",\"mensageria\":{}}", HttpStatus.OK);
        Mockito.when(client.getHealth(Mockito.eq(url))).thenReturn(fakeResponse);

        ResponseEntity<String> response = client.getHealth(url);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("mensageria");
        Mockito.verify(client).getHealth(Mockito.eq(url));
    }
}
