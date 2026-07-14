package com.zc.shorturl.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortUrlConstant {
    public static final String LONG_TO_SHORT_CACHE_NAME = "long2short";
    public static final String SHORT_TO_LONG_CACHE_NAME = "short2long";
}
