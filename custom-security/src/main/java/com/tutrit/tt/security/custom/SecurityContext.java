package com.tutrit.tt.security.custom;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SecurityContext {

    public static Principal currentUser() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session =  attr.getRequest().getSession(false);
            Principal principal = (Principal) session.getAttribute("principal");
            return principal;
        } catch (Exception e) {
            return null;
        }
    }
}
