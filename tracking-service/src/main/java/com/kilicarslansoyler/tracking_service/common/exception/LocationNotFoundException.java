package com.kilicarslansoyler.tracking_service.common.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long courierId) {
        super("Location data not found for courier with id: " + courierId);
    }
}
