package com.linkcut.shortener.modules.urls.service;

import com.linkcut.shortener.modules.urls.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkCleanupService {

    private final LinkRepository linkRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void deleteExpiredLinks() {
        Instant now = Instant.now();

        int deleted = linkRepository.deleteByExpiresAtBefore(now);

        log.info("Deleted {} expired links at {}", deleted, now);
    }
}