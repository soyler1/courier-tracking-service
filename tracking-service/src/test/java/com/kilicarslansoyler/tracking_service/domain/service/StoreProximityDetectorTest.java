package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.domain.event.CourierEnteredStoreEvent;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import com.kilicarslansoyler.tracking_service.infrastructure.cache.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

class StoreProximityDetectorTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private LocationDistanceCalculator distanceCalculator;

    @Mock
    private RedisCacheService cacheService;

    @InjectMocks
    private StoreProximityDetector detector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        detector = new StoreProximityDetector(eventPublisher, distanceCalculator, cacheService);
        // Alan enjeksiyonu (entryDistanceThreshold ve entryCooldownSeconds)
        ReflectionTestUtils.setField(detector, "entryDistanceThreshold", 100.0);
        ReflectionTestUtils.setField(detector, "entryCooldownSeconds", 60L);
    }

    @Test
    void shouldPublishEventIfWithinDistanceAndCooldownPassed() {
        CourierLocation location = CourierLocation.builder()
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        Store store = new Store("Test Store", 40.0005, 29.0005);

        when(distanceCalculator.calculate(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(50.0);

        when(cacheService.isEligibleToLogEntry(eq(1L), eq("Test Store"), any(), any(LocalDateTime.class)))
                .thenReturn(true);

        detector.detect(location, List.of(store));

        verify(eventPublisher, times(1)).publishEvent(any(CourierEnteredStoreEvent.class));
        verify(cacheService, times(1)).updateEntryTime(eq(1L), eq("Test Store"), any());
    }

    @Test
    void shouldNotPublishEventIfOutsideDistance() {
        CourierLocation location = CourierLocation.builder()
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        Store store = new Store("Far Store", 41.0, 30.0);

        when(distanceCalculator.calculate(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(200.0); // fazla uzak

        detector.detect(location, List.of(store));

        verify(eventPublisher, never()).publishEvent(any());
        verify(cacheService, never()).updateEntryTime(any(), any(), any());
    }

    @Test
    void shouldNotPublishEventIfCooldownNotPassed() {
        CourierLocation location = CourierLocation.builder()
                .courierId(2L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        Store store = new Store("Cooldown Store", 40.0001, 29.0001);

        when(distanceCalculator.calculate(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(50.0);

        when(cacheService.isEligibleToLogEntry(eq(2L), eq("Cooldown Store"), any(), any(LocalDateTime.class)))
                .thenReturn(false); // henüz tekrar girişe izin yok

        detector.detect(location, List.of(store));

        verify(eventPublisher, never()).publishEvent(any());
        verify(cacheService, never()).updateEntryTime(any(), any(), any());
    }
}
