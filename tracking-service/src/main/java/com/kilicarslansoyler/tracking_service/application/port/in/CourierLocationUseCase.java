package com.kilicarslansoyler.tracking_service.application.port.in;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;

public interface CourierLocationUseCase {

    CourierLocation saveLocation(CourierLocation location);

    double getTotalTravelDistance(Long courierId);
}
