package com.linkcut.shortener.modules.urls.exception;

public class KeyAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "This key is already taken";

    public KeyAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public KeyAlreadyExistsException(String message) {
        super(message);
    }
}