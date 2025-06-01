package com.kilicarslansoyler.tracking_service.adapter.out.store;

import com.kilicarslansoyler.tracking_service.domain.model.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "store-service", url = "${store-service.url}")
public interface StoreServiceClient {

    @GetMapping("/api/stores")
    List<Store> getAllStores();
}
