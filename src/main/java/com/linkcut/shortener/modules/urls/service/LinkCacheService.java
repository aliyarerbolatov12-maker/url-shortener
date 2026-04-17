package com.linkcut.shortener.modules.urls.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkCacheService {
    private static final String URL_PREFIX = "url:";
    private static final String CLICKS_PREFIX = "clicks:";
    private static final Duration TTL = Duration.ofDays(1);
    private final StringRedisTemplate redisTemplate;

    public void cacheUrl(String key, String url) {
        redisTemplate.opsForValue().set(URL_PREFIX + key, url, TTL);
    }

    public String getUrl(String key) {
        return redisTemplate.opsForValue().get(URL_PREFIX + key);
    }

    public void deleteUrlCache(String key) {
        redisTemplate.delete(URL_PREFIX + key);
    }

    public void deleteEverything(String key) {
        redisTemplate.delete(List.of(URL_PREFIX + key, CLICKS_PREFIX + key));
    }

    public void incrementClicks(String key) {
        redisTemplate.opsForValue().increment(CLICKS_PREFIX + key);
    }

    public List<Long> getClicks(List<String> keys) {
        List<String> fullKeys = keys.stream().map(k -> CLICKS_PREFIX + k).toList();
        List<String> values = redisTemplate.opsForValue().multiGet(fullKeys);
        if (values == null) return Collections.nCopies(keys.size(), 0L);

        return values.stream()
                .map(v -> v == null ? 0L : Long.parseLong(v))
                .toList();
    }

    public long getClicks(String key) {
        String val = redisTemplate.opsForValue().get(CLICKS_PREFIX + key);
        return val == null ? 0L : Long.parseLong(val);
    }
}