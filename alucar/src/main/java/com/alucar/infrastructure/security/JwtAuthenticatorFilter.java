package com.alucar.infrastructure.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component ("jwtAuthenticationFilter")
public class JwtAuthenticatorFilter extends OncePerRequestFilter {
    
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Implement JWT authentication logic here
        filterChain.doFilter(request, response);
    }
}
