package com.kilicarslansoyler.tracking_service.adapter.in.web.mapper;

import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationResponseDTO;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import org.springframework.stereotype.Component;

@Component
public class CourierLocationWebMapper {

    public CourierLocation toDomain(CourierLocationRequestDTO dto) {
        return CourierLocation.builder()
                .courierId(dto.getCourierId())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .timestamp(dto.getTimestamp())
                .build();
    }

    public CourierLocationResponseDTO toResponseDTO(CourierLocation domain) {
        return CourierLocationResponseDTO.builder()
                .id(domain.getId())
                .courierId(domain.getCourierId())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .timestamp(domain.getTimestamp())
                .build();
    }
}
