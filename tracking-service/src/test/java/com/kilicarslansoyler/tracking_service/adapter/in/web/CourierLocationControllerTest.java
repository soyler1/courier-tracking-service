package com.kilicarslansoyler.tracking_service.adapter.in.web;

import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationResponseDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.mapper.CourierLocationWebMapper;
import com.kilicarslansoyler.tracking_service.application.port.in.CourierLocationUseCase;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourierLocationControllerTest {

    @Mock
    private CourierLocationUseCase useCase;

    @Mock
    private CourierLocationWebMapper mapper;

    @InjectMocks
    private CourierLocationController controller;

    private CourierLocationRequestDTO requestDTO;
    private CourierLocation domainModel;
    private CourierLocationResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new CourierLocationRequestDTO(1L, 40.0, 29.0, LocalDateTime.now());

        domainModel = CourierLocation.builder()
                .id(10L)
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(requestDTO.getTimestamp())
                .build();

        responseDTO = CourierLocationResponseDTO.builder()
                .id(10L)
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(requestDTO.getTimestamp())
                .build();
    }

    @Test
    void shouldSaveCourierLocation() {
        when(mapper.toDomain(requestDTO)).thenReturn(domainModel);
        when(useCase.saveLocation(domainModel)).thenReturn(domainModel);
        when(mapper.toResponseDTO(domainModel)).thenReturn(responseDTO);

        ResponseEntity<CourierLocationResponseDTO> response = controller.saveLocation(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(mapper).toDomain(requestDTO);
        verify(useCase).saveLocation(domainModel);
        verify(mapper).toResponseDTO(domainModel);
    }

    @Test
    void shouldGetTotalTravelDistance() {
        Long courierId = 1L;
        double expectedDistance = 1234.56;

        when(useCase.getTotalTravelDistance(courierId)).thenReturn(expectedDistance);

        ResponseEntity<Double> response = controller.getTotalDistance(courierId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDistance, response.getBody());
        verify(useCase).getTotalTravelDistance(courierId);
    }
}
