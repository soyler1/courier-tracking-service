package com.kilicarslansoyler.store_service.infrastructure.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kilicarslansoyler.store_service.domain.factory.StoreFactory;
import com.kilicarslansoyler.store_service.domain.model.Store;
import com.kilicarslansoyler.store_service.infrastructure.cache.StoreCache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreJsonLoader {

    private final StoreCache storeCache;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("stores.json")) {
            JsonNode root = objectMapper.readTree(input);
            List<Store> storeList = new ArrayList<>();
            for (JsonNode node : root) {
                storeList.add(StoreFactory.fromJson(node));
            }
            storeCache.setStores(storeList);
            log.info("Loaded {} stores", storeList.size());
        } catch (Exception e) {
            throw new RuntimeException("Error loading stores.json", e);
        }
    }
}
