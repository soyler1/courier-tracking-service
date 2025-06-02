package com.kilicarslansoyler.tracking_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.out.store.StoreServiceClient;
import com.kilicarslansoyler.tracking_service.config.MockStoreClientConfig;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository.CourierLocationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TrackingServiceApplication.class, MockStoreClientConfig.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class CourierLocationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourierLocationJpaRepository locationRepository;

    @Autowired
    private StoreServiceClient storeClient;

    private final Long courierId = 42L;

    @BeforeEach
    void setUp() {
        locationRepository.deleteAll();

        when(storeClient.getAllStores()).thenReturn(List.of(
                new Store("Dummy Store", 0.0, 0.0)
        ));
    }

    @Test
    void shouldSaveCourierLocationAndCalculateDistance() throws Exception {
        CourierLocationRequestDTO location1 = new CourierLocationRequestDTO(
                courierId, 40.9923307, 29.1244229, LocalDateTime.now().minusMinutes(5));

        CourierLocationRequestDTO location2 = new CourierLocationRequestDTO(
                courierId, 40.986106, 29.1161293, LocalDateTime.now());

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Gateway", "true")
                        .content(objectMapper.writeValueAsString(location1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Gateway", "true")
                        .content(objectMapper.writeValueAsString(location2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/locations/" + courierId + "/distance")
                        .header("X-Api-Gateway", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDistanceTravelled").value(matchesPattern("^\\d+[.,]\\d{2} km$")));

    }
}
