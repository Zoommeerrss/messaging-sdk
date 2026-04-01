package com.messaging.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "mensageria")
public class MensageriaProperties {
    private EmailProperties email = new EmailProperties();
    private WhatsappProperties whatsapp = new WhatsappProperties();

    public void setEmail(EmailProperties email) { this.email = email; }

    public void setWhatsapp(WhatsappProperties whatsapp) { this.whatsapp = whatsapp; }

    @Getter
    @Setter
    public static class EmailProperties {
        private boolean enabled;
        private String host;
        private int port;
        private String username;
        private String password;
        // getters e setters
    }

    @Getter
    @Setter
    public static class WhatsappProperties {
        private boolean enabled;
        private String apiUrl;
        private String token;
        // getters e setters
    }
}
