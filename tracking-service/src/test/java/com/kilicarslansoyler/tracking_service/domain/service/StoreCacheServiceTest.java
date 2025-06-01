package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.adapter.out.store.StoreServiceClient;
import com.kilicarslansoyler.tracking_service.common.exception.StoreServiceUnavailableException;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StoreCacheServiceTest {

    private StoreServiceClient storeServiceClient;
    private StoreCacheService storeCacheService;

    @BeforeEach
    void setUp() {
        storeServiceClient = mock(StoreServiceClient.class);
        storeCacheService = new StoreCacheService(storeServiceClient);
    }

    @Test
    void shouldRefreshCacheSuccessfully_whenStoreServiceIsAvailable() {
        List<Store> mockStores = List.of(
                new Store("Store A", 40.0, 29.0),
                new Store("Store B", 41.0, 30.0)
        );

        when(storeServiceClient.getAllStores()).thenReturn(mockStores);

        storeCacheService.refreshStores();

        assertEquals(mockStores, storeCacheService.getStores());
        verify(storeServiceClient, times(1)).getAllStores();
    }


    @Test
    void shouldThrowStoreServiceUnavailableException_whenStoreServiceFails() {
        when(storeServiceClient.getAllStores()).thenThrow(mock(FeignException.class));

        StoreServiceUnavailableException exception = assertThrows(
                StoreServiceUnavailableException.class,
                () -> storeCacheService.refreshStores()
        );

        assertEquals("Store service is not available", exception.getMessage());
        verify(storeServiceClient, times(1)).getAllStores();
    }

}
