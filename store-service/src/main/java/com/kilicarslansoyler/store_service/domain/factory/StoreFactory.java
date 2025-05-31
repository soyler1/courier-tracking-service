package com.kilicarslansoyler.store_service.domain.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.kilicarslansoyler.store_service.domain.model.Store;

public class StoreFactory {

    public static Store fromJson(JsonNode node) {
        return new Store(
                node.get("name").asText(),
                node.get("lat").asDouble(),
                node.get("lng").asDouble()
        );
    }
}

