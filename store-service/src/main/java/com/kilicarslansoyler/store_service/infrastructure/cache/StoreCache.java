package com.kilicarslansoyler.store_service.infrastructure.cache;

import com.kilicarslansoyler.store_service.domain.model.Store;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Getter
public class StoreCache {

    private final List<Store> stores = new CopyOnWriteArrayList<>();

    public void setStores(List<Store> storeList) {
        stores.clear();
        stores.addAll(storeList);
    }
}

