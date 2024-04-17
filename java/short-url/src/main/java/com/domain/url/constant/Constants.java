package com.domain.url.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    // 短链接使用域名
    public static final String DOMAIN_SHORT = "https://short.com/";

    // 短 -> 长链接映射关系，K: 短链接，V: 长链接
    public static final ConcurrentHashMap<String, String> URL_MAP = new ConcurrentHashMap<>();
}
