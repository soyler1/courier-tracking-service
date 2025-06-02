package com.kilicarslansoyler.tracking_service.adapter.out.store;

import com.kilicarslansoyler.tracking_service.domain.model.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Profile("!test")
@FeignClient(name = "storeService", url = "${store-service.url}")
public interface StoreServiceClient {

    @GetMapping(value = "/api/stores", headers = "X-Internal-Request=tracking-service")
    List<Store> getAllStores();
}

