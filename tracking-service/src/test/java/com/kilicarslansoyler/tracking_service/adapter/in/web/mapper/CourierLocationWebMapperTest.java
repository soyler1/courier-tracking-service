package com.kilicarslansoyler.tracking_service.adapter.in.web.mapper;

import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationResponseDTO;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CourierLocationWebMapperTest {

    private CourierLocationWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CourierLocationWebMapper();
    }

    @Test
    void shouldMapRequestDTOToDomainModel() {
        // given
        CourierLocationRequestDTO requestDTO = new CourierLocationRequestDTO(
                1L, 40.0, 29.0, LocalDateTime.now()
        );

        // when
        CourierLocation domain = mapper.toDomain(requestDTO);

        // then
        assertEquals(requestDTO.getCourierId(), domain.getCourierId());
        assertEquals(requestDTO.getLatitude(), domain.getLatitude());
        assertEquals(requestDTO.getLongitude(), domain.getLongitude());
        assertEquals(requestDTO.getTimestamp(), domain.getTimestamp());
    }

    @Test
    void shouldMapDomainModelToResponseDTO() {
        // given
        CourierLocation domain = CourierLocation.builder()
                .id(10L)
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        // when
        CourierLocationResponseDTO responseDTO = mapper.toResponseDTO(domain);

        // then
        assertEquals(domain.getId(), responseDTO.getId());
        assertEquals(domain.getCourierId(), responseDTO.getCourierId());
        assertEquals(domain.getLatitude(), responseDTO.getLatitude());
        assertEquals(domain.getLongitude(), responseDTO.getLongitude());
        assertEquals(domain.getTimestamp(), responseDTO.getTimestamp());
    }
}
