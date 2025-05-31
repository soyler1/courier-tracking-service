package com.kilicarslansoyler.store_service.adapter.in.rest;

import com.kilicarslansoyler.store_service.application.service.StoreQueryService;
import com.kilicarslansoyler.store_service.domain.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreQueryService storeQueryService;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeQueryService.getAllStores());
    }
}
