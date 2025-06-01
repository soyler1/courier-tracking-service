package com.kilicarslansoyler.tracking_service.domain.command;

import com.kilicarslansoyler.tracking_service.application.EntryLogRecorder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreEntryLogCommandHandler implements CommandHandler<CourierEnteredStoreCommand> {

    private final EntryLogRecorder recorder;

    @Override
    public void handle(CourierEnteredStoreCommand command) {
        recorder.recordEntry(command.getCourierId(), command.getStoreName());
    }
}
