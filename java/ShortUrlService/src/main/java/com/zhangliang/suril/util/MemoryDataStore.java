package com.zhangliang.suril.util;

import com.zhangliang.suril.configuration.CodeMessageConfiguration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 内存数据存储
 *
 * @author zhang
 * @date 2021/12/01
 */
@Component
public class MemoryDataStore {

    /**
     * 初始化大小
     */
    @Value("${config.init-capacity}")
    private Integer initCapacity;

    /**
     * 最大容量
     */
    @Value("${config.max-capacity}")
    private Integer maxCapacity;

    /**
     * 仓储
     */
    public Map<String, String> StoreData;

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    @PostConstruct
    private void init() throws Exception {
        HashMap<String, String> storted = new HashMap<>(initCapacity);
        StoreData = Collections.synchronizedMap(storted);
    }

    /**
     * 获取一个原始的额url
     *
     * @param shortUrl 短网址
     * @return {@link String}
     */
    public String get(String shortUrl) {
        AssertUtils.isUrl(shortUrl);
        return StoreData.get(shortUrl);
    }

    /**
     * 设置
     *
     * 这里会判断是否已经超过容量最大值
     *
     * @param shortUrl 短网址
     * @param originalUrl 原始url
     * @return {@link String}
     */
    public String set(String shortUrl, String originalUrl) {
        AssertUtils.isUrl(shortUrl);
        AssertUtils.isUrl(originalUrl);
        if (maxCapacity < StoreData.size()) {
            throw new IllegalArgumentException(CodeMessageConfiguration.getMessage(90001));
        }

        return StoreData.put(shortUrl, originalUrl);
    }

    /**
     * 是否已经存在当前短地址
     *
     * @param shortUrl 短网址
     * @return boolean
     */
    public boolean exists(String shortUrl) {
        AssertUtils.isUrl(shortUrl);

        return StoreData.containsKey(shortUrl);
    }
}
