package com.kilicarslansoyler.tracking_service.adapter.in.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourierLocationRequestDTO {
    private Long courierId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
