package com.linkcut.shortener.modules.urls.service;

import com.linkcut.shortener.modules.urls.dto.CreateLinkRequestDto;
import com.linkcut.shortener.modules.urls.dto.LinkDto;
import com.linkcut.shortener.modules.urls.dto.UpdateLinkRequestDto;
import com.linkcut.shortener.modules.urls.entity.Link;
import com.linkcut.shortener.modules.urls.exception.LinkNotFoundException;
import com.linkcut.shortener.modules.urls.helper.BuildShortUrl;
import com.linkcut.shortener.modules.urls.mapper.LinkCustomMapper;
import com.linkcut.shortener.modules.urls.mapper.LinkMapper;
import com.linkcut.shortener.modules.urls.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkService {

    private final LinkRepository repository;
    private final Base62Service base62;
    private final LinkMapper mapper;
    private final KeyService keyService;
    private final SequenceService sequenceService;
    private final BuildShortUrl buildShortUrl;
    private final LinkCacheService cacheService;
    private final LinkCustomMapper customMapper;

    @Transactional
    public LinkDto shorten(CreateLinkRequestDto dto) {
        Link link = new Link();
        link.setLongUrl(dto.longUrl());

        String key = StringUtils.hasText(dto.shortKey())
                ? dto.shortKey()
                : base62.encode(sequenceService.getNextShortKeySequence());

        if (StringUtils.hasText(dto.shortKey())) {
            keyService.assertKeyAvailable(key, null);
        }

        link.setShortKey(key);
        Link saved = repository.save(link);

        cacheService.cacheUrl(saved.getShortKey(), saved.getLongUrl());

        return customMapper.toDto(saved, 0L, buildShortUrl.buildShortUrl(key));
    }

    public String getOriginalUrl(String key) {
        String cached = cacheService.getUrl(key);
        if (cached != null) return cached;

        String urlFromDb = repository.findLongUrlByShortKey(key)
                .orElseThrow(LinkNotFoundException::new);

        cacheService.cacheUrl(key, urlFromDb);
        return urlFromDb;
    }

    public List<LinkDto> getAllLinks() {
        List<Link> links = repository.findAll();
        List<String> keys = links.stream().map(Link::getShortKey).toList();
        List<Long> clicks = cacheService.getClicks(keys);

        return IntStream.range(0, links.size())
                .mapToObj(i -> {
                    Link link = links.get(i);
                    return customMapper.toDto(
                            link,
                            clicks.get(i),
                            buildShortUrl.buildShortUrl(link.getShortKey()));
                })
                .toList();
    }

    public LinkDto getLinkById(Long id) {
        Link link = repository.findById(id)
                .orElseThrow(LinkNotFoundException::new);

        Long clicks = cacheService.getClicks(link.getShortKey());
        String shortUrl = buildShortUrl.buildShortUrl(link.getShortKey());

        return customMapper.toDto(link, clicks, shortUrl);
    }

    public String incrementClicksAndGetUrl(String key) {
        String longUrl = getOriginalUrl(key);
        cacheService.incrementClicks(key);
        return longUrl;
    }

    @Transactional
    public void deleteLink(Long id) {
        Link link = repository.findById(id).orElseThrow(LinkNotFoundException::new);
        String key = link.getShortKey();

        repository.delete(link);
        cacheService.deleteEverything(key);
    }

    @Transactional
    public LinkDto updateLink(Long id, UpdateLinkRequestDto dto) {
        Link link = repository.findById(id).orElseThrow(LinkNotFoundException::new);
        String oldKey = link.getShortKey();

        if (dto.shortKey() != null) {
            keyService.assertKeyAvailable(dto.shortKey(), oldKey);
        }

        mapper.updateEntityFromDto(dto, link);
        Link updated = repository.save(link);

        if (!oldKey.equals(updated.getShortKey())) {
            cacheService.deleteEverything(oldKey);
        } else {
            cacheService.deleteUrlCache(oldKey);
        }
        cacheService.cacheUrl(updated.getShortKey(), updated.getLongUrl());

        return customMapper.toDto(updated,
                cacheService.getClicks(updated.getShortKey()),
                buildShortUrl.buildShortUrl(updated.getShortKey()));
    }
}