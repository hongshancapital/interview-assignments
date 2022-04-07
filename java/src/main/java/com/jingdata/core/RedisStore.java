package com.jingdata.core;

import com.jingdata.constant.StrategyEnum;
import org.springframework.stereotype.Component;

/**
 * redis存储
 *
 * @Author
 * @Date
 */
@Component
public class RedisStore implements Store {
    @Override
    public String getStrategyName() {
        return StrategyEnum.REDIS.getName();
    }

    @Override
    public boolean overFlow() {
        return false;
    }

    @Override
    public String write(String shortCode, String longUrl) {
        return null;
    }

    @Override
    public String read(String shortCode) {
        return null;
    }
}
