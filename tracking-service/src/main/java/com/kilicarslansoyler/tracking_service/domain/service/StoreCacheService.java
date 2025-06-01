package com.kilicarslansoyler.tracking_service.domain.service;

import com.kilicarslansoyler.tracking_service.common.exception.StoreServiceUnavailableException;
import com.kilicarslansoyler.tracking_service.domain.model.Store;
import com.kilicarslansoyler.tracking_service.adapter.out.store.StoreServiceClient;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class StoreCacheService {

    private final StoreServiceClient storeServiceClient;

    private List<Store> cachedStores = Collections.emptyList();

    @PostConstruct
    public void init() {
        log.info("Initializing store cache at application startup.");
        refreshStores();
    }

    @Scheduled(fixedRate = 10 * 60 * 1000) // 10 dakikada bir yenile
    public void refreshStores() {
        try {
            cachedStores = storeServiceClient.getAllStores();
            log.info("Store cache updated with {} stores.", cachedStores.size());
        } catch (FeignException e) {
            log.error("Failed to refresh store cache", e);
            throw new StoreServiceUnavailableException("Store service is not available", e);
        }
    }

    public List<Store> getStores() {
        return cachedStores;
    }
}
