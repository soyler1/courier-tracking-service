package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.repository.CourierLocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourierLocationServiceTest {

    private CourierLocationRepository repository;
    private LocationDistanceCalculator distanceCalculator;
    private CourierLocationService service;

    @BeforeEach
    void setUp() {
        repository = mock(CourierLocationRepository.class);
        distanceCalculator = mock(LocationDistanceCalculator.class);
        service = new CourierLocationService(repository, distanceCalculator);
    }

    @Test
    void shouldSaveLocation() {
        CourierLocation input = CourierLocation.builder()
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        CourierLocation saved = CourierLocation.builder()
                .id(10L)
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(input.getTimestamp())
                .build();

        when(repository.save(input)).thenReturn(saved);

        CourierLocation result = service.saveLocation(input);

        assertEquals(saved, result);
        verify(repository).save(input);
    }

    @Test
    void shouldReturnTotalDistanceForMultiplePoints() {
        CourierLocation loc1 = new CourierLocation(null, 1L, 40.0, 29.0, LocalDateTime.now().minusMinutes(2));
        CourierLocation loc2 = new CourierLocation(null, 1L, 40.1, 29.1, LocalDateTime.now());

        when(repository.findByCourierId(1L)).thenReturn(List.of(loc1, loc2));
        when(distanceCalculator.calculate(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude()))
                .thenReturn(5000.0);

        double totalDistance = service.getTotalTravelDistance(1L);

        assertEquals(5000.0, totalDistance);
    }

    @Test
    void shouldReturnZeroDistanceForSingleLocation() {
        CourierLocation loc = new CourierLocation(null, 1L, 40.0, 29.0, LocalDateTime.now());
        when(repository.findByCourierId(1L)).thenReturn(List.of(loc));

        double totalDistance = service.getTotalTravelDistance(1L);

        assertEquals(0.0, totalDistance);
    }
}
