package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TokenValidatorController {

    TokenService tokenService;

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody Principal principal) {
        return tokenService.validateToken(principal);
    }
}
