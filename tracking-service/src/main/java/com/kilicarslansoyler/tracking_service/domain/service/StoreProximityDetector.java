package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.domain.event.CourierEnteredStoreEvent;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import com.kilicarslansoyler.tracking_service.infrastructure.cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreProximityDetector {

    private final ApplicationEventPublisher eventPublisher;
    private final LocationDistanceCalculator distanceCalculator;
    private final RedisCacheService cacheService;

    @Value("${tracking.store-entry-distance-threshold:100}") // metre
    private double entryDistanceThreshold;

    @Value("${tracking.store-entry-cooldown-seconds:60}") // saniye
    private long entryCooldownSeconds;

    public void detect(CourierLocation location, List<Store> storeList) {
        for (Store store : storeList) {
            double distance = distanceCalculator.calculate(
                    location.getLatitude(), location.getLongitude(),
                    store.getLatitude(), store.getLongitude());

            if (distance <= entryDistanceThreshold) {
                boolean eligible = cacheService.isEligibleToLogEntry(
                        location.getCourierId(), store.getName(),
                        Duration.ofSeconds(entryCooldownSeconds)
                );

                if (eligible) {
                    cacheService.updateEntryTime(
                            location.getCourierId(),
                            store.getName(),
                            LocalDateTime.now()
                    );

                    eventPublisher.publishEvent(new CourierEnteredStoreEvent(
                            this, location.getCourierId(), store.getName()));
                }
            }
        }
    }
}