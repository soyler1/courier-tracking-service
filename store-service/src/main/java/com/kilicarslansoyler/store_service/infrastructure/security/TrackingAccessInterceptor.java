package com.kilicarslansoyler.store_service.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TrackingAccessInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME = "X-Internal-Request";
    private static final String EXPECTED_VALUE = "tracking-service";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(HEADER_NAME);

        if (EXPECTED_VALUE.equals(header)) {
            return true;
        }

        log.warn("Unauthorized access to Store Service from IP: {}", request.getRemoteAddr());
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied. Only Tracking Service can access this endpoint.");
        return false;
    }
}
