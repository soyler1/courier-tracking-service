package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.repository.CourierLocationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class CourierLocationService {

    private final CourierLocationRepository courierLocationRepository;
    private final LocationDistanceCalculator distanceCalculator;

    public CourierLocation saveLocation(CourierLocation location) {
        return courierLocationRepository.save(location);
    }

    public double getTotalTravelDistance(Long courierId) {
        List<CourierLocation> locations = courierLocationRepository.findByCourierId(courierId).stream()
                .sorted(Comparator.comparing(CourierLocation::getTimestamp))
                .toList();

        if (locations.size() < 2) return 0.0;

        double total = 0.0;
        for (int i = 1; i < locations.size(); i++) {
            var prev = locations.get(i - 1);
            var curr = locations.get(i);
            total += distanceCalculator.calculate(
                    prev.getLatitude(), prev.getLongitude(),
                    curr.getLatitude(), curr.getLongitude());
        }

        return total;
    }
}
