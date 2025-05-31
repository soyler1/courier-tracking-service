package com.kilicarslansoyler.store_service.application.service.impl;

import com.kilicarslansoyler.store_service.domain.model.Store;
import com.kilicarslansoyler.store_service.infrastructure.cache.StoreCache;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreQueryServiceImplTest {

    @Test
    void getAllStores_shouldReturnStoresFromCache() {
        // Arrange
        StoreCache mockCache = mock(StoreCache.class);
        List<Store> mockList = List.of(new Store("Store A", 40.0, 29.0));
        when(mockCache.getStores()).thenReturn(mockList);

        StoreQueryServiceImpl service = new StoreQueryServiceImpl(mockCache);

        // Act
        List<Store> result = service.getAllStores();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Store A", result.get(0).getName());
        verify(mockCache, times(1)).getStores();
    }
}
