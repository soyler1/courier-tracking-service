package com.kilicarslansoyler.tracking_service.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourierLocationResponseDTO {
    private Long id;
    private Long courierId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
