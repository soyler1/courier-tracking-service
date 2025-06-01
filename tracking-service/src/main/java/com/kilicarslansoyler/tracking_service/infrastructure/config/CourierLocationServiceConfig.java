package com.kilicarslansoyler.tracking_service.infrastructure.config;

import com.kilicarslansoyler.tracking_service.domain.repository.CourierLocationRepository;
import com.kilicarslansoyler.tracking_service.domain.service.CourierLocationService;
import com.kilicarslansoyler.tracking_service.domain.service.LocationDistanceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourierLocationServiceConfig {

    @Bean
    CourierLocationService courierLocationService(CourierLocationRepository repository,
                                                  LocationDistanceCalculator distanceCalculator) {
        return new CourierLocationService(repository, distanceCalculator);
    }
}

