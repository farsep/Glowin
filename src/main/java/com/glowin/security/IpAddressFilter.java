package com.glowin.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class IpAddressFilter extends OncePerRequestFilter {

    @Value("${allowed_ip:0:0:0:0:0:0:0:1}")
    private String ALLOWED_IP;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        String requestUri = request.getRequestURI();

        if (("/categorias-servicios/all".equals(requestUri) || "/servicios/all".equals(requestUri)) && !ALLOWED_IP.equals(clientIp)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}