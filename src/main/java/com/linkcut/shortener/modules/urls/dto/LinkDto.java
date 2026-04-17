package com.linkcut.shortener.modules.urls.dto;

public record LinkDto(
        Long id,
        String longUrl,
        String shortUrl,
        Long clicks
) {
}