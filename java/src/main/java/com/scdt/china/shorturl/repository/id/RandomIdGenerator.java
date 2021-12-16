package com.scdt.china.shorturl.repository.id;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.PrimitiveIterator;
import java.util.stream.LongStream;

/**
 * 自增长ID生成器
 *
 * @author ng-life
 */
public class RandomIdGenerator implements IdGenerator {

    PrimitiveIterator.OfLong longGenerator;

    public RandomIdGenerator(long randomNumberOrigin, long randomNumberBound) throws NoSuchAlgorithmException {
        SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
        LongStream longs = instanceStrong.longs(randomNumberOrigin, randomNumberBound);
        this.longGenerator = longs.iterator();
    }

    @Override
    public synchronized Long nextId() {
        return longGenerator.nextLong();
    }

    @Override
    public boolean isRandom() {
        return true;
    }

}
