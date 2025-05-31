package com.kilicarslansoyler.store_service.domain.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kilicarslansoyler.store_service.domain.model.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreFactoryTest {

    @Test
    void fromJson_shouldReturnValidStore() throws Exception {
        String json = """
            {
              "name": "Test Migros",
              "lat": 40.123,
              "lng": 29.456
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);

        Store store = StoreFactory.fromJson(node);

        assertEquals("Test Migros", store.getName());
        assertEquals(40.123, store.getLatitude());
        assertEquals(29.456, store.getLongitude());
    }
}
