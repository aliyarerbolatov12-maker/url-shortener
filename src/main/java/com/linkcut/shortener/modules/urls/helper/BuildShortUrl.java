package com.linkcut.shortener.modules.urls.helper;

import com.linkcut.shortener.common.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildShortUrl {

    private final AppConfig appConfig;

    public String buildShortUrl(String shortKey) {
        return appConfig.baseUrl() + "/" + shortKey;
    }
}
