package com.kilicarslansoyler.tracking_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierLocation {

    private Long id;
    private Long courierId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
