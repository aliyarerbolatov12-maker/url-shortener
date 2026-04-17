package com.linkcut.shortener.modules.urls.mapper;

import com.linkcut.shortener.modules.urls.dto.LinkDto;
import com.linkcut.shortener.modules.urls.entity.Link;
import org.springframework.stereotype.Component;

@Component
public class LinkCustomMapper {

    public LinkDto toDto(Link link, Long clicks, String shortUrl) {
        if (link == null) return null;

        return new LinkDto(
                link.getId(),
                link.getLongUrl(),
                shortUrl,
                clicks
        );
    }
}