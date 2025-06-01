package com.kilicarslansoyler.tracking_service.application.usecase;

import com.kilicarslansoyler.tracking_service.application.port.in.CourierLocationUseCase;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.service.CourierLocationService;
import com.kilicarslansoyler.tracking_service.domain.service.StoreCacheService;
import com.kilicarslansoyler.tracking_service.domain.service.StoreProximityDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierLocationServiceImpl implements CourierLocationUseCase {

    private final CourierLocationService domainService;
    private final StoreCacheService storeCacheService;
    private final StoreProximityDetector proximityDetector;

    @Override
    public CourierLocation saveLocation(CourierLocation location) {
        CourierLocation saved = domainService.saveLocation(location);

        proximityDetector.detect(saved, storeCacheService.getStores());

        return saved;
    }

    @Override
    public double getTotalTravelDistance(Long courierId) {
        return domainService.getTotalTravelDistance(courierId);
    }
}
