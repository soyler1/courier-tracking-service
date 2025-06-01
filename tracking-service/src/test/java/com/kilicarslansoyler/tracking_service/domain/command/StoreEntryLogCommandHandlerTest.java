package com.kilicarslansoyler.tracking_service.domain.command;

import com.kilicarslansoyler.tracking_service.application.EntryLogRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StoreEntryLogCommandHandlerTest {

    private EntryLogRecorder recorder;
    private StoreEntryLogCommandHandler handler;

    @BeforeEach
    void setUp() {
        recorder = mock(EntryLogRecorder.class);
        handler = new StoreEntryLogCommandHandler(recorder);
    }

    @Test
    void shouldDelegateCommandToRecorder() {
        // given
        Long courierId = 42L;
        String storeName = "Ataşehir MMM Migros";
        CourierEnteredStoreCommand command = new CourierEnteredStoreCommand(courierId, storeName);

        // when
        handler.handle(command);

        // then
        verify(recorder, times(1)).recordEntry(courierId, storeName);
    }
}
