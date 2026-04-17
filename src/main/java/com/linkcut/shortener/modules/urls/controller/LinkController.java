package com.linkcut.shortener.modules.urls.controller;

import com.linkcut.shortener.modules.urls.dto.CreateLinkRequestDto;
import com.linkcut.shortener.modules.urls.dto.LinkDto;
import com.linkcut.shortener.modules.urls.dto.UpdateLinkRequestDto;
import com.linkcut.shortener.modules.urls.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public LinkDto shorten(@Valid @RequestBody CreateLinkRequestDto dto) {
        return linkService.shorten(dto);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key) {
        String longUrl = linkService.incrementClicksAndGetUrl(key);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", longUrl)
                .build();
    }

    @GetMapping("/info/{key}")
    public String getUrl(@PathVariable String key) {
        return linkService.getOriginalUrl(key);
    }

    @GetMapping("/details/{id}")
    public LinkDto getDetails(@PathVariable Long id) {
        return linkService.getLinkById(id);
    }

    @GetMapping
    public List<LinkDto> getAll() {
        return linkService.getAllLinks();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        linkService.deleteLink(id);
    }

    @PutMapping("/{id}")
    public LinkDto updateLink(@PathVariable long id, @Valid @RequestBody UpdateLinkRequestDto dto) {
        return linkService.updateLink(id, dto);
    }
}