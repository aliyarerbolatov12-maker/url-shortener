package com.linkcut.shortener.modules.urls.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Service {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARACTERS.length();

    public String encode(Long input) {
        StringBuilder encodedString = new StringBuilder();
        while (input > 0) {
            encodedString.append(CHARACTERS.charAt((int) (input % BASE)));
            input /= BASE;
        }
        return encodedString.reverse().toString();
    }

    public Long decode(String input) {
        long result = 0;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            int value = CHARACTERS.indexOf(currentChar);
            if (value == -1) {
                throw new IllegalArgumentException("Invalid character: " + currentChar);
            }

            result = result * BASE + value;
        }

        return result;
    }
}