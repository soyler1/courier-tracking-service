package com.kilicarslansoyler.tracking_service.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
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

    public boolean isEligibleToLogEntry(Long courierId, String storeName, Duration cooldown, LocalDateTime referenceTime) {
        return getLastEntryTime(courierId, storeName)
                .map(last -> last.plus(cooldown).isBefore(referenceTime))
                .orElse(true);
    }

    private String buildKey(Long courierId, String storeName) {
        String key = "entrylog:" + courierId + ":" + storeName.replace(" ", "_");
        log.info("Redis Entry Key = {}", key);
        return key;
    }
}
