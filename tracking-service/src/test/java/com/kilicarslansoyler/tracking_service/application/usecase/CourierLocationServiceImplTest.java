package com.kilicarslansoyler.tracking_service.application.usecase;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import com.kilicarslansoyler.tracking_service.domain.service.CourierLocationService;
import com.kilicarslansoyler.tracking_service.domain.service.StoreCacheService;
import com.kilicarslansoyler.tracking_service.domain.service.StoreProximityDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class CourierLocationServiceImplTest {

    private CourierLocationService domainService;
    private StoreCacheService storeCacheService;
    private StoreProximityDetector proximityDetector;
    private CourierLocationServiceImpl service;

    @BeforeEach
    void setUp() {
        domainService = mock(CourierLocationService.class);
        storeCacheService = mock(StoreCacheService.class);
        proximityDetector = mock(StoreProximityDetector.class);
        service = new CourierLocationServiceImpl(domainService, storeCacheService, proximityDetector);
    }

    @Test
    void shouldSaveLocationAndDetectProximity() {
        // given
        CourierLocation input = CourierLocation.builder()
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        CourierLocation saved = input.builder().id(100L).build();
        List<Store> stores = List.of(new Store("Test Store", 40.0, 29.0));

        when(domainService.saveLocation(input)).thenReturn(saved);
        when(storeCacheService.getStores()).thenReturn(stores);

        // when
        CourierLocation result = service.saveLocation(input);

        // then
        assertThat(result).isEqualTo(saved);
        verify(domainService).saveLocation(input);
        verify(storeCacheService).getStores();
        verify(proximityDetector).detect(saved, stores);
    }

    @Test
    void shouldGetTotalTravelDistance() {
        // given
        Long courierId = 1L;
        double expectedDistance = 12345.67;

        when(domainService.getTotalTravelDistance(courierId)).thenReturn(expectedDistance);

        // when
        double result = service.getTotalTravelDistance(courierId);

        // then
        assertThat(result).isEqualTo(expectedDistance);
        verify(domainService).getTotalTravelDistance(courierId);
    }
}
