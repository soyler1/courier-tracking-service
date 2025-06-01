package com.kilicarslansoyler.tracking_service.common.exception;

public class StoreServiceUnavailableException extends RuntimeException {

    public StoreServiceUnavailableException(String message) {
        super(message);
    }

    public StoreServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
