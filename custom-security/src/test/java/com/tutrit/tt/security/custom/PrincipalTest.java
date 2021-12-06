package com.tutrit.tt.security.custom;


import org.junit.Assert;
import org.junit.Test;

public class PrincipalTest {

    @Test
    public void deserializeFromString() {
        String[] roles = {"ADMIN", "USER"};
        Principal principal = new Principal();
        principal.setId(1L);
        principal.setIsAuthenticated(true);
        principal.setFullName("Maksim");
        principal.setRoles(roles);

        String serialized = principal.serializeToString();

        Principal principal1 = Principal.deserializeFromString(serialized);
        Assert.assertEquals(principal, principal1);
    }
}