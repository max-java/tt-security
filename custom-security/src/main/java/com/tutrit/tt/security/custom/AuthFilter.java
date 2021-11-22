package com.tutrit.tt.security.custom;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login") && request.getMethod().toLowerCase().equals("post")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("principal") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO: 11/20/21 how to redirect?
        response.sendRedirect("/login");
    }
}
