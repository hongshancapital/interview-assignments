package com.example.tinyurl.service;

/**
 * Tiny Url Generator service
 * @author hermitriver
 */
public interface TinyUrlGenerator {
    /**
     * Generate the tiny url
     * @param targetUrl long target url
     * @return tiny url
     */
    public String generate(String targetUrl);
}
