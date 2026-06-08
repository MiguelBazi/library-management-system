package com.library.library_management_system.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationClient {

    private final RestTemplate restTemplate;

    @Value("${email.service.url}")
    private String emailServiceUrl;

    public void sendEmail(Map<String, String> emailRequest) {
        try {
            log.info("Calling email service for: {}", emailRequest.get("email"));
            restTemplate.postForObject(emailServiceUrl + "/send-email", emailRequest, String.class);
        } catch (Exception e) {
            log.error("Failed to call email service: {}", e.getMessage());
        }
    }
}