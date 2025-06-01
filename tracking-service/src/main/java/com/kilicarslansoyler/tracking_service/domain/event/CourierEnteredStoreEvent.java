package com.kilicarslansoyler.tracking_service.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CourierEnteredStoreEvent extends ApplicationEvent {

    private final Long courierId;
    private final String storeName;

    public CourierEnteredStoreEvent(Object source, Long courierId, String storeName) {
        super(source);
        this.courierId = courierId;
        this.storeName = storeName;
    }
}
