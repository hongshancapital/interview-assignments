package demo.service.impl;

import demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RedisServiceImpl
 * @Description: redis 服务类实现
 * @author Xia
 * @version V1.0
 * @Date 2021/12/15
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public long incr(String value) {
        return redisTemplate.opsForValue().increment(value);
    }
}
