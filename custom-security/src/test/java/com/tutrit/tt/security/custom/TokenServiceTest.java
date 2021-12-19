package com.tutrit.tt.security.custom;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class TokenServiceTest {

    @Test
    public void generateToken() throws Exception{
        TokenService tokenService = getTokenService();
        assertEquals("dbe4f70c36", tokenService.generateToken(makePrincipal()));
    }

    @Test
    public void validateTokenPositive() throws Exception{
        TokenService tokenService = getTokenService();
        Principal principal = makePrincipal();
        principal.setToken("dbe4f70c36");
        assertTrue(tokenService.validateToken(principal));
    }

    @Test
    public void validateTokenNegative() throws Exception{
        TokenService tokenService = getTokenService();
        Principal principal = makePrincipal();
        principal.setToken("0004f70c36");
        assertFalse(tokenService.validateToken(principal));
    }

    private TokenService getTokenService() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = TokenService.class;
        TokenService tokenService = (TokenService) clazz.newInstance();

        Field secretKey = tokenService.getClass().getDeclaredField("secretKey");
        secretKey.setAccessible(true);
        secretKey.set(tokenService, "1234");
        return tokenService;
    }

    private Principal makePrincipal() {
        String[] roles = {"ADMIN", "USER"};
        Principal principal = new Principal();
        principal.setId(1L);
        principal.setIsAuthenticated(true);
        principal.setFullName("Maksim");
        principal.setRoles(roles);
        return principal;
    }
}
