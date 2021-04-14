package io.github.cubesky.scdtjava.service;

import org.springframework.stereotype.Component;

@Component
public class UrlGenerateService {
    private static final char[] validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private static final int MAX;

    static {
        int max = 1;
        for (int i = 0; i<8;i++) max *= validChars.length;;
        MAX = max;
    }

    private int count = 0;

    public synchronized String getShortUrl() {
        if (count > MAX) return null;
        StringBuilder builder = new StringBuilder(8);
        for (int i = 7; i >= 1; i--) {
            builder.append(validChars[(count / (validChars.length * i)) % validChars.length]);
        }
        builder.append(validChars[count % validChars.length]);
        count++;
        return builder.toString();
    }

    public synchronized void reset() {
        count = 0;
    }
}
