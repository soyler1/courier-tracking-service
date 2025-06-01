package com.kilicarslansoyler.tracking_service.application.impl;

import com.kilicarslansoyler.tracking_service.application.EntryLogRecorder;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.EntryLog;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository.EntryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryLogRecorderImpl implements EntryLogRecorder {

    private final EntryLogRepository repository;

    @Override
    public void recordEntry(Long courierId, String storeName) {
        EntryLog entry = EntryLog.builder()
                .courierId(courierId)
                .storeName(storeName)
                .timestamp(LocalDateTime.now())
                .build();

        repository.save(entry);
        log.info("EntryLog saved: Courier [{}] - Store [{}]", courierId, storeName);
    }
}
