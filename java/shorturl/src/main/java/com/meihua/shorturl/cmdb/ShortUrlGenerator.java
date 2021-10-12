package com.meihua.shorturl.cmdb;




import com.meihua.shorturl.util.IDGeneratorUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public interface ShortUrlGenerator {

    /**
     * 生命周期方法
     */
     void destroy();

    /**
     * 生命周期方法
     */
     void init();

    /**
     * 根据短链获取长链
     * @param key 短链key
     * @return 长链
     */
     String getValue(String key);

    /**
     * 根据长链生成短链并存储到内存
     * @param url
     * @return 短链
     */
     String put(String url);

}
