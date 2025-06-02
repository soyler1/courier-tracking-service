package com.kilicarslansoyler.tracking_service.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class GatewayRequestInterceptor implements HandlerInterceptor {

    private static final String GATEWAY_HEADER = "X-Api-Gateway";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String gatewayHeader = request.getHeader(GATEWAY_HEADER);

        if ("true".equalsIgnoreCase(gatewayHeader)) {
            return true;
        }

        log.warn("Unauthorized access attempt to Tracking Service from IP: {}", request.getRemoteAddr());
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied. Requests must come through API Gateway.");
        return false;
    }
}
