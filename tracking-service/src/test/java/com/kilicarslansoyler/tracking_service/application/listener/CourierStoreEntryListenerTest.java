package com.kilicarslansoyler.tracking_service.application.listener;

import com.kilicarslansoyler.tracking_service.domain.command.CourierEnteredStoreCommand;
import com.kilicarslansoyler.tracking_service.domain.command.StoreEntryLogCommandHandler;
import com.kilicarslansoyler.tracking_service.domain.event.CourierEnteredStoreEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CourierStoreEntryListenerTest {

    private StoreEntryLogCommandHandler handler;
    private CourierStoreEntryListener listener;

    @BeforeEach
    void setUp() {
        handler = mock(StoreEntryLogCommandHandler.class);
        listener = new CourierStoreEntryListener(handler);
    }

    @Test
    void shouldHandleCourierEnteredStoreEvent() {
        // given
        Long courierId = 1L;
        String storeName = "Ataşehir MMM Migros";
        CourierEnteredStoreEvent event = new CourierEnteredStoreEvent(this, courierId, storeName);

        // when
        listener.handle(event);

        // then
        CourierEnteredStoreCommand expectedCommand = new CourierEnteredStoreCommand(courierId, storeName);
        verify(handler, times(1)).handle(refEq(expectedCommand));
    }
}
