package com.tutrit.tt.security.custom;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Disabled after moving to module as a bean
 */
//@Service
public class AuthRoboService {
    public static final String applicationId = "checkpoint";
    public static final String applicationPass = "1234";

    public boolean authorize(final HttpServletRequest request) {
        String id = request.getHeader("applicationId");
        String pass = request.getHeader("applicationPass");
        if (applicationId.equals(id) && applicationPass.equals(pass)) {
            return true;
        }
        return false;
    }
}
