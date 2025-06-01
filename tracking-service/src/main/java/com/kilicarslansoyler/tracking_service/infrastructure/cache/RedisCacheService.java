package com.kilicarslansoyler.tracking_service.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final StringRedisTemplate redisTemplate;

    public Optional<LocalDateTime> getLastEntryTime(Long courierId, String storeName) {
        String key = buildKey(courierId, storeName);
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Optional.of(LocalDateTime.parse(value, formatter)) : Optional.empty();
    }

    public void updateEntryTime(Long courierId, String storeName, LocalDateTime timestamp) {
        String key = buildKey(courierId, storeName);
        redisTemplate.opsForValue().set(key, timestamp.format(formatter));
    }

    public boolean isEligibleToLogEntry(Long courierId, String storeName, Duration cooldown) {
        return getLastEntryTime(courierId, storeName)
                .map(last -> last.plus(cooldown).isBefore(LocalDateTime.now()))
                .orElse(true);
    }

    private String buildKey(Long courierId, String storeName) {
        return "entrylog:" + courierId + ":" + storeName.replace(" ", "_");
    }
}
