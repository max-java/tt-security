package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthProxy {

    AuthServerProvider authServerProvider;

    public boolean validatePrincipal(Principal principal) {

        final String baseUrl = authServerProvider.getUrl() + "/validateToken";
        try {
            URI uri = new URI(baseUrl);
            HttpEntity<Principal> request =
                    new HttpEntity<>(principal, null);
            boolean isTokenValid = new RestTemplate().postForObject(uri, request, Boolean.class);
            if(isTokenValid) {
                log("yes");
                return true;
            }
            log("Invalid token");
            throw new RuntimeException("Invalid token");
        } catch (Exception e) {
            log("Auth server request failed");
            throw new RuntimeException("Auth server request failed");
        }
    }

    private void log(String message) {
        LoggerFactory.getLogger(AuthProxy.class).info("token validation result: {}", message);
    }
}
