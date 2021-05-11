package com.example.shortlink.infrastructure.codec;

public interface CodecStrategy {
    String encode(long uniqueId);

    long decode(String message);
}
