package com.kilicarslansoyler.tracking_service.domain.repository;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;

import java.util.List;

public interface CourierLocationRepository {

    CourierLocation save(CourierLocation location);

    List<CourierLocation> findByCourierId(Long courierId);

    List<CourierLocation> findAll();
}
