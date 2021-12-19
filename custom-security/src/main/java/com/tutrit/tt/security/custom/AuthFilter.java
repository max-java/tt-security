package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tutrit.tt.security.custom.MyProperties.REDIRECTED_URL_ATTRIBUTE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthFilter extends OncePerRequestFilter {

    AuthorizationProvider authorizationProvider;
    AuthRoboProvider authRoboProvider;
    AuthProvider authProvider;
    AuthServerProvider authServerProvider;
    AuthProxy authProxy;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (authProvider.isEnabled()) {
            logRequest(request);
            if (request.getRequestURI().equals("/login") && request.getMethod().toLowerCase().equals("post")) {
                filterChain.doFilter(request, response);
                return;
            }

            if (request.getRequestURI().equals("/login")) {
                filterChain.doFilter(request, response);
                return;
            }

            if (request.getRequestURI().equals("/validateToken")) {
                filterChain.doFilter(request, response);
                return;
            }


            if (request.getSession().getAttribute("principal") != null) {
                Principal principal = (Principal) request.getSession().getAttribute("principal");
                authProxy.validatePrincipal(principal);
                if (authorizationProvider.isUserHasAccess(request.getRequestURI(), principal)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                response.sendRedirect("/403");
                return;
            }

            if(request.getParameter("principal") != null && !request.getParameter("principal").isBlank()) {

                Principal principal = Principal.deserializeFromString(request.getParameter("principal"));
                authProxy.validatePrincipal(principal);
                request.getSession().setAttribute("principal", principal);
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (authRoboProvider.isEnabled()) {
            if (request.getHeader("applicationId") != null) {
                authRoboProvider.authorize(request);
                filterChain.doFilter(request, response);
                return;
            }
        }

        // TODO: 12/4/21
//        String encodedRedirectedUrl = UriUtil.encode("/login?"+REDIRECTED_URL_ATTRIBUTE+"="+authServerProvider.getRedirectedUrl());

        response.sendRedirect(authServerProvider.getUrl()+"/login?"+REDIRECTED_URL_ATTRIBUTE+"="+authServerProvider.getRedirectedUrl());
    }

    private void logRequest(HttpServletRequest request) {
        LoggerFactory.getLogger(AuthFilter.class).info("{}", request.getRequestURI());
    }
}
