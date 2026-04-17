package com.linkcut.shortener.modules.urls.service;

import com.linkcut.shortener.modules.urls.exception.KeyAlreadyExistsException;
import com.linkcut.shortener.modules.urls.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class KeyService {

    private final LinkRepository repository;

    public void assertKeyAvailable(String key, String currentKey) {
        if (repository.existsByShortKey(key) && !Objects.equals(key, currentKey)) {
            throw new KeyAlreadyExistsException();
        }
    }
}