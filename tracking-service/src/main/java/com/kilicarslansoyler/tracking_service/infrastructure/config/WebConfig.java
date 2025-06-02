package com.kilicarslansoyler.tracking_service.infrastructure.config;

import com.kilicarslansoyler.tracking_service.common.security.GatewayRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final GatewayRequestInterceptor gatewayRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(gatewayRequestInterceptor)
                .addPathPatterns("/api/**");
    }
}


