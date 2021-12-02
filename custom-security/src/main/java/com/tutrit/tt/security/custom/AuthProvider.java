package com.tutrit.tt.security.custom;

import org.springframework.stereotype.Component;

@Component
public interface AuthProvider {
    Long getUserId();
    String getUserPass();
    String[] getUserRoles();

    default boolean authorize(final Long id, final String pass) {
        if (getUserPass().equals(pass) && getUserId() == id) {
            return true;
        }
        return false;
    }

    default boolean isEnabled() {
        return true;
    }
}
