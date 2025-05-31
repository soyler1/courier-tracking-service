package com.kilicarslansoyler.store_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String name;
    private double latitude;
    private double longitude;
}

