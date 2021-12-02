package com.tutrit.tt.security.custom;

import org.springframework.stereotype.Service;

/**
 * Disabled after moving to module as a bean
 */
//@Service
public class AuthService {
    public static final long userId = 1;
    public static final String password = "1";

    public boolean authorize(final Long id, final String pass) {
        if (password.equals(pass) && userId == id) {
            return true;
        }
        return false;
    }
}
