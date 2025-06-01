package com.kilicarslansoyler.tracking_service.application;

public interface EntryLogRecorder {
    void recordEntry(Long courierId, String storeName);
}
