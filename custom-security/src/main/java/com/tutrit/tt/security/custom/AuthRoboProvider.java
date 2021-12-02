package com.tutrit.tt.security.custom;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface AuthRoboProvider {
    String getApplicationId();
    String getApplicationPass();

    default boolean authorize(final HttpServletRequest request) {
        String id = request.getHeader("applicationId");
        String pass = request.getHeader("applicationPass");
        if (getApplicationId().equals(id) && getApplicationPass().equals(pass)) {
            return true;
        }
        return false;
    }

    default boolean isEnabled() {
        return false;
    }
}
