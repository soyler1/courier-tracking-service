package com.kilicarslansoyler.tracking_service.domain.command;

import lombok.Value;

@Value
public class CourierEnteredStoreCommand {
    Long courierId;
    String storeName;
}
