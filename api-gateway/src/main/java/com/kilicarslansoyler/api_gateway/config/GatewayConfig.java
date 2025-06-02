package com.kilicarslansoyler.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${gateway.tracking-service.uri}")
    private String trackingServiceUri;

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tracking-service", r -> r.path("/api/locations/**", "/api/distance/**")
                        .filters(f -> f.addRequestHeader("X-Api-Gateway", "true"))
                        .uri(trackingServiceUri))
                .build();
    }
}
