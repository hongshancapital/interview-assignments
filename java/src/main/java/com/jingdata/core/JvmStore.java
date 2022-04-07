package com.jingdata.core;

import com.jingdata.constant.StrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * jvm存储
 *
 * @Author
 * @Date
 */
@Component
public class JvmStore implements Store{

    @Value("${jingdata.jvm.limit}")
    private Integer limit;

    private static ConcurrentHashMap<String, String> shortCache = new ConcurrentHashMap<String, String>();

    @Override
    public String getStrategyName() {
        return StrategyEnum.JVM.getName();
    }

    @Override
    public boolean overFlow() {
        return shortCache.size() > limit;
    }

    @Override
    public String write(String shortCode, String longUrl) {
        return shortCache.put(shortCode, longUrl);
    }

    @Override
    public String read(String shortCode) {
        return shortCache.get(shortCode);
    }
}
