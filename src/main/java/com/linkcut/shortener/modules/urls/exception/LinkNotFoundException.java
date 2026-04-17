package com.linkcut.shortener.modules.urls.exception;

public class LinkNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Link not found";

    public LinkNotFoundException(String message) {
        super(message);
    }

    public LinkNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
