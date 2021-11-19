package com.tutrit.tt.security.checkpoint.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
// this doesn't work because java.net.http.HttpRequest not supporting patch
public class CheckPointServiceRestTemplate {
    public static final String[] cards = {"1111", "2222", "3333", "4444", "5555", "6666"};
    public static final String[] statuses = {"true", "false"};
    RestTemplate restTemplate;

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void checkinByRandomCardIdWithRestTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/plain");
        HttpEntity<String> statusUpdate = new HttpEntity<>(statuses[randomArrayIndex(statuses.length)], headers);

        String cardId = cards[randomArrayIndex(cards.length)];
        try {
            ResponseEntity<String> respEntity = restTemplate
                    .exchange("http://localhost:8082/api/card/"+cardId, HttpMethod.PATCH, statusUpdate, String.class);
            log.info("{}", respEntity);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    private int randomArrayIndex(int arraySize) {
        return ThreadLocalRandom.current().nextInt(0, arraySize);
    }
}
