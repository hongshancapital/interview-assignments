package com.duoji.shortlink.ability;

import com.duoji.shortlink.ability.generator.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 11:45:00
 */
@Service
@Slf4j
public class NumberGeneratorSelectAbility {

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 随机选择生成取号器
     *
     * @param numberGeneratorList 号码生成器列表
     * @return
     */
    public NumberGenerator selectOneRandom(List<NumberGenerator> numberGeneratorList) {
        //加读锁
        try {
            rwl.readLock().lock();
            if (numberGeneratorList == null || numberGeneratorList.size() <= 0) {
                return null;
            }
            int rnd = new SecureRandom().nextInt(numberGeneratorList.size());
            return numberGeneratorList.get(rnd);
        } finally {
            rwl.readLock().unlock();
        }

    }

    /**
     * 按权重获取取号期
     *
     * @param numberGeneratorList
     * @return
     */
    public NumberGenerator selectOneWeight(List<NumberGenerator> numberGeneratorList) {
        //加读锁
        try {
            rwl.readLock().lock();
            if (numberGeneratorList == null || numberGeneratorList.size() <= 0) {
                return null;
            }
            int[] weight = {0, 0, 0, 0, 1, 2, 3, 4, 5, 5};
            int rnd = new SecureRandom().nextInt(weight.length);
            return numberGeneratorList.get(weight[rnd]);
        } finally {
            rwl.readLock().unlock();
        }

    }

    public void removeOneNumberGenerator(List<NumberGenerator> numberGeneratorList, NumberGenerator numberGenerator) {
        //加写锁
        try {
            rwl.writeLock().lock();
            numberGeneratorList.removeIf(x -> x.ownId().equals(numberGenerator.ownId()));
        } finally {
            rwl.writeLock().unlock();
        }

    }
}
