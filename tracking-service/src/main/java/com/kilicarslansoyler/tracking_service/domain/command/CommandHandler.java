package com.kilicarslansoyler.tracking_service.domain.command;

public interface CommandHandler<T> {
    void handle(T command);
}
