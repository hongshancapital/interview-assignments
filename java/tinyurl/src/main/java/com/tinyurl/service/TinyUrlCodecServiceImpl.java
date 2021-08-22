package com.tinyurl.service;

import org.springframework.stereotype.Service;

@Service
public class TinyUrlCodecServiceImpl implements TinyUrlCodecService {
    private RandomTinyUrlCodec randomTinyUrlCodec = new RandomTinyUrlCodec();

    @Override
    public String encode(String url) {
        return randomTinyUrlCodec.encode(url);
    }

    @Override
    public String decode(String url) {
        return randomTinyUrlCodec.decode(url);
    }
}
