package com.pulse.config.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MdcFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_URI = "requestUri";
    private static final String REQUEST_METHOD = "requestMethod";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String requestId = UUID.randomUUID().toString();
            MDC.put(REQUEST_ID, requestId);
            MDC.put(REQUEST_URI, request.getRequestURI());
            MDC.put(REQUEST_METHOD, request.getMethod());

            LOGGER.info("Incoming request");
            filterChain.doFilter(request, response);
            LOGGER.info("Request completed with status: {}", response.getStatus());

        } finally {

            MDC.clear();

        }

    }

}
