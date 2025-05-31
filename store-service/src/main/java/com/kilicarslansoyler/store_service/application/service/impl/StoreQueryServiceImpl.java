package com.kilicarslansoyler.store_service.application.service.impl;

import com.kilicarslansoyler.store_service.application.service.StoreQueryService;
import com.kilicarslansoyler.store_service.domain.model.Store;
import com.kilicarslansoyler.store_service.infrastructure.cache.StoreCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreCache storeCache;

    @Override
    public List<Store> getAllStores() {
        return storeCache.getStores();
    }
}
