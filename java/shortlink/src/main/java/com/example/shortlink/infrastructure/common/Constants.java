package com.example.shortlink.infrastructure.common;

public final class Constants {
    public static final int CACHE_MAX_SIZE = 10000000;
    public static final int CACHE_EXPIRE_DAYS = 30;
    public static final int BLOOM_FILTER_SIZE = 10000000;
    public static final double BLOOM_FILTER_FPP = 0.001;

    private Constants() {
    }
}
