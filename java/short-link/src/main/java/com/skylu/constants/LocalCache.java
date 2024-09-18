package com.skylu.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName LocalCache.java
 * @Description 本地缓存
 * @createTime 2022年04月22日 15:39:00
 */
public class LocalCache {

    /**
     * 一次删除100条数据
     */
    public static final Integer BATCH_NUM = 100;

    /**
     * 最大缓存100w
     */
    public static final Long MAX = 1000000L;

    /**
     * 短域名为key,长域名为value
     */
    public static final Map<String, String> CACHE = new LinkedHashMap<>();

    /**
     * 长域名为key,短域名为value
     */
    public static final Map<String, String> RECORD = new LinkedHashMap<>();
}
