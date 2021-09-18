package com.example.app.common.constants;

/**
 * 常量
 *
 * @author voidm
 * @date 2021/9/18
 */
public class Constants {

    /**
     * LRU 缓存最大数量
     */
    public static final int LRU_CACHE_MAX_LEN = 1000;

    /**
     * 唯一ID长度
     */
    public static final int KEY_MAX_LEN = 8;

    /**
     * 主域名
     */
    public static final String DOMAIN = "https://a.cn/";

    /**
     * 正则表达式，匹配url
     */
    public static final String REGEX_URL = "^(http[s]{0,1}?:\\/\\/)?([\\w-]+\\.)+[\\w-]+(\\/[\\w-./?%&=]*)?$";
    /**
     * URL 长度过滤
     */
    public static final int URL_MAX_LENGTH = 200;
}