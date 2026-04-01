package com.messaging.config;

import org.springframework.http.ResponseEntity;

public interface HealthClient {
    ResponseEntity<String> getHealth(String url);
}

