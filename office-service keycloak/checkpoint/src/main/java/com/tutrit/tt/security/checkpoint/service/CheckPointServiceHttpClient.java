package com.tutrit.tt.security.checkpoint.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
// this doesn't work because java.net.http.HttpRequest not supporting patch
public class CheckPointServiceHttpClient {
    HttpClient httpClient;

//    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void checkinByRandomCardId() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/card/5555"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/plain")
                .PUT(HttpRequest.BodyPublishers.ofString("true"))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("{}", response.statusCode());
            log.info("{}", response.body());
        } catch (Exception e) {
            log.info("{}", e.getMessage(), e);
        }
    }
}
