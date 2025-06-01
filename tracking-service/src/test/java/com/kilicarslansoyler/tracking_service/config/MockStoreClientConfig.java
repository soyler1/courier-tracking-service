package com.kilicarslansoyler.tracking_service.config;

import com.kilicarslansoyler.tracking_service.adapter.out.store.StoreServiceClient;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class MockStoreClientConfig {

    @Bean
    @Primary
    public StoreServiceClient storeServiceClient() {
        StoreServiceClient mock = mock(StoreServiceClient.class);
        when(mock.getAllStores()).thenReturn(List.of(
                new Store("Mock Store", 0.0, 0.0)
        ));
        return mock;
    }
}
