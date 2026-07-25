package org.zxl.proxy;

import org.springframework.stereotype.Component;
import org.zxl.domain.Segment;
import org.zxl.util.DomainUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 存储服务
 */
@Component
public class StoreProxy {
    /**
     * 最大存储的链接个数
     */
    private static int MAX_CACHE_COUNT = 500000;

    /**
     * 起始值设置为 a00001 这代表一个 62进制的数字 [a-z][A-Z][0-9]
     */
    private AtomicReference<String> state = new AtomicReference("a00001");

    private LinkedHashMap<String, String> shortToLongMap = new LinkedHashMap() {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            if (size() >= MAX_CACHE_COUNT) {
                return true;
            }
            return false;
        }
    };

    private LinkedHashMap<String, String> longToShortMap = new LinkedHashMap() {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            if (size() >= MAX_CACHE_COUNT) {
                return true;
            }
            return false;
        }
    };

    /**
     * 加载一段62进制数字范围 用于生成短连接
     * 通常 state是存储在 mysql等数据库中做集中保存的 这里简化为jvm内存中
     *
     * @return
     */
    public Segment loadSegment(int step) {
        String start = state.get();
        String end = DomainUtils.add(start, step);
        while (true) {
            if (state.compareAndSet(start, end)) {
                break;
            } else {
                start = state.get();
                end = DomainUtils.add(start, step);
            }
        }
        return new Segment(start, end);
    }

    public void store(String longUrl, String shortUrl) {
        shortToLongMap.put(shortUrl, longUrl);
        longToShortMap.put(longUrl, shortUrl);
    }

    public String recover(String shortUrl) {
        return shortToLongMap.get(shortUrl);
    }

    public String exits(String longUrl) {
        return longToShortMap.get(longUrl);
    }

}
