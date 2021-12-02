package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthFilter extends OncePerRequestFilter {

    AuthorizationProvider authorizationProvider;
    AuthRoboProvider authRoboProvider;
    AuthProvider authProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(authProvider.isEnabled()) {
            if(request.getRequestURI().equals("/login") && request.getMethod().toLowerCase().equals("post")) {
                filterChain.doFilter(request, response);
                return;
            }

            if(request.getRequestURI().equals("/login")) {
                filterChain.doFilter(request, response);
                return;
            }

            if(request.getSession().getAttribute("principal") != null) {
                Principal principal = (Principal) request.getSession().getAttribute("principal");
                if (authorizationProvider.isUserHasAccess(request.getRequestURI(), principal)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                response.sendRedirect("/403");
                return;
            }
        }

        if(authRoboProvider.isEnabled()) {
            if(request.getHeader("applicationId") != null) {
                authRoboProvider.authorize(request);
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.sendRedirect("/login");
    }
}
