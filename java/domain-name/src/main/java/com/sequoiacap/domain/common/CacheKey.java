package com.sequoiacap.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum CacheKey {

    /**
     * 可访问的压缩码映射
     */
    ACCESS_CODE_HASH("octopus:access:code:hash", "可访问的压缩码映射", -1L);

    private final String key;
    private final String description;
    private final long expireSeconds;
}
