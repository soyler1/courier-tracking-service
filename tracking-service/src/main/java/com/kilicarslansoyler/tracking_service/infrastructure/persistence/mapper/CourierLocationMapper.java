package com.kilicarslansoyler.tracking_service.infrastructure.persistence.mapper;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.CourierLocationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CourierLocationMapper {

    public CourierLocationJpaEntity toEntity(CourierLocation domain) {
        return CourierLocationJpaEntity.builder()
                .id(domain.getId())
                .courierId(domain.getCourierId())
                .timestamp(domain.getTimestamp())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .build();
    }

    public CourierLocation toDomain(CourierLocationJpaEntity entity) {
        return CourierLocation.builder()
                .id(entity.getId())
                .courierId(entity.getCourierId())
                .timestamp(entity.getTimestamp())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}