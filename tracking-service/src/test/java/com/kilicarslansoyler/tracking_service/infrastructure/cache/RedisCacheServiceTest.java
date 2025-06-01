package com.kilicarslansoyler.tracking_service.infrastructure.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisCacheServiceTest {

    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private RedisCacheService redisCacheService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(StringRedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        redisCacheService = new RedisCacheService(redisTemplate);
    }

    @Test
    void shouldReturnEmptyIfKeyNotFound() {
        when(valueOperations.get(anyString())).thenReturn(null);

        Optional<LocalDateTime> result = redisCacheService.getLastEntryTime(1L, "Test Store");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCorrectTimestampIfKeyExists() {
        LocalDateTime now = LocalDateTime.now();
        when(valueOperations.get(anyString())).thenReturn(now.format(formatter));

        Optional<LocalDateTime> result = redisCacheService.getLastEntryTime(1L, "Test Store");

        assertTrue(result.isPresent());
        assertEquals(now.format(formatter), result.get().format(formatter));
    }

    @Test
    void shouldUpdateEntryTime() {
        LocalDateTime now = LocalDateTime.now();

        redisCacheService.updateEntryTime(1L, "Test Store", now);

        verify(valueOperations).set("entrylog:1:Test_Store", now.format(formatter));
    }

    @Test
    void shouldBeEligibleIfNoPreviousEntry() {
        when(valueOperations.get(anyString())).thenReturn(null);

        boolean eligible = redisCacheService.isEligibleToLogEntry(1L, "Test Store", Duration.ofSeconds(60));

        assertTrue(eligible);
    }

    @Test
    void shouldBeEligibleIfCooldownPassed() {
        LocalDateTime past = LocalDateTime.now().minusMinutes(2);
        when(valueOperations.get(anyString())).thenReturn(past.format(formatter));

        boolean eligible = redisCacheService.isEligibleToLogEntry(1L, "Test Store", Duration.ofSeconds(60));

        assertTrue(eligible);
    }

    @Test
    void shouldNotBeEligibleIfCooldownNotPassed() {
        LocalDateTime recent = LocalDateTime.now().minusSeconds(30);
        when(valueOperations.get(anyString())).thenReturn(recent.format(formatter));

        boolean eligible = redisCacheService.isEligibleToLogEntry(1L, "Test Store", Duration.ofSeconds(60));

        assertFalse(eligible);
    }
}
