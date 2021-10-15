package com.example.assignment.service;

import com.example.assignment.Exception.ShortCodeUseOutException;

public interface ShortUrlService {

    String generate(String originalUrl) throws ShortCodeUseOutException;

    String parse(String shortUrl);
}
