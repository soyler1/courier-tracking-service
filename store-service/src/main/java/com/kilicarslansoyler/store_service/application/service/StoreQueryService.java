package com.kilicarslansoyler.store_service.application.service;

import com.kilicarslansoyler.store_service.domain.model.Store;

import java.util.List;

public interface StoreQueryService {
    List<Store> getAllStores();
}
