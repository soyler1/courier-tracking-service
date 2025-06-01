package com.kilicarslansoyler.tracking_service.application.listener;

import com.kilicarslansoyler.tracking_service.domain.command.CourierEnteredStoreCommand;
import com.kilicarslansoyler.tracking_service.domain.command.StoreEntryLogCommandHandler;
import com.kilicarslansoyler.tracking_service.domain.event.CourierEnteredStoreEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourierStoreEntryListener {

    private final StoreEntryLogCommandHandler handler;

    @Async("taskExecutor")
    @EventListener
    public void handle(CourierEnteredStoreEvent event) {
        CourierEnteredStoreCommand command = new CourierEnteredStoreCommand(
                event.getCourierId(), event.getStoreName()
        );
        handler.handle(command);
    }
}
