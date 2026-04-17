package com.linkcut.shortener.modules.urls.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UpdateLinkRequestDto(
        @URL(message = "Invalid URL")
        @Size(max = 255, message = "URL must be at most 255 characters")
        String longUrl,

        @Size(min = 1, max = 50, message = "Short key must be between 1 and 50 characters")
        String shortKey
) {
}