package com.kilicarslansoyler.tracking_service.application.impl;

import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.EntryLog;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository.EntryLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EntryLogRecorderImplTest {

    private EntryLogRepository repository;
    private EntryLogRecorderImpl recorder;

    @BeforeEach
    void setUp() {
        repository = mock(EntryLogRepository.class);
        recorder = new EntryLogRecorderImpl(repository);
    }

    @Test
    void shouldRecordEntryWithCorrectFields() {
        // given
        Long courierId = 1L;
        String storeName = "Ataşehir MMM Migros";

        // when
        recorder.recordEntry(courierId, storeName);

        // then
        ArgumentCaptor<EntryLog> captor = ArgumentCaptor.forClass(EntryLog.class);
        verify(repository, times(1)).save(captor.capture());

        EntryLog saved = captor.getValue();
        assertThat(saved.getCourierId()).isEqualTo(courierId);
        assertThat(saved.getStoreName()).isEqualTo(storeName);
        assertThat(saved.getTimestamp()).isNotNull();
        assertThat(saved.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
