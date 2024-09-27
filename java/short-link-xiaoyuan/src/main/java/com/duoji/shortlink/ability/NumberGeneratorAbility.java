package com.duoji.shortlink.ability;

import com.duoji.shortlink.ability.generator.AutoincrementNumberGenerator;
import com.duoji.shortlink.ability.generator.CacheQueueNumberGenerator;
import com.duoji.shortlink.ability.generator.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月17日 21:21:00
 */
@Service
@Slf4j
public class NumberGeneratorAbility {

    /**
     * 获取计数器列表
     *
     * @param n
     * @param highMax
     * @param step
     * @return
     */
    public List<NumberGenerator> createAutoincrementNumberGenerator(Long n, Long highMax, Long step) {
        List<NumberGenerator> list = new ArrayList<>();
        for (Long i = 0L; i < n; i++) {
            list.add(new AutoincrementNumberGenerator(i, highMax, step));
            log.info("[createAutoincrementNumberGenerator],自增计数器创建,id={}", i);
        }
        return list;
    }

    /**
     * 获取队列发号器
     *
     * @param n
     * @param highMax
     * @param step
     * @return
     */
    public List<NumberGenerator> createCacheQueueNumberGenerator(Long n, Long highMax, Long step) {
        List<NumberGenerator> list = new ArrayList<>();
        for (Long i = 0L; i < n; i++) {
            list.add(new CacheQueueNumberGenerator(i, highMax, step, 128));
            log.info("[createAutoincrementNumberGenerator],队列发号器创建,id={}", i);
        }
        return list;
    }
}
