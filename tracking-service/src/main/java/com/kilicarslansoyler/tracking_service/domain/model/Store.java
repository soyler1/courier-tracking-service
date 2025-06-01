package com.kilicarslansoyler.tracking_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String name;
    private double latitude;
    private double longitude;
}
