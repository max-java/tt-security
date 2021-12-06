package com.tutrit.tt.security.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrincipalMapper {

    ObjectMapper objectMapper;

    public String writePrincipal(Principal principal) {
        try {
            return objectMapper.writeValueAsString(principal);
        } catch (Exception e) {
            return "";
        }
    }

    public Principal readPrincipal(String principal) {
        try {
            return objectMapper.readValue(principal, Principal.class);
        } catch (Exception e) {
            return new Principal();
        }
    }
}
