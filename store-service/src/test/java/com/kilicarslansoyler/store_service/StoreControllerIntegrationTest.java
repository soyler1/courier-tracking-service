package com.kilicarslansoyler.store_service;

import com.kilicarslansoyler.store_service.domain.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void getAllStores_shouldReturnStoreList() {
        String url = "http://localhost:" + port + "/api/stores";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Request", "tracking-service"); // interceptor için gerekli header

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Store>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

}
