package com.scdt.china.interview.manager;

import com.scdt.china.interview.cache.DomainCache;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sujiale
 */
public class DomainManager {
    public static final int SHORT_DOMAIN_MAX_LENGTH = 8;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Random RANDOM = new Random();
    private static final String RANDOM_VALUES = "0123456789abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getAndSetShortDomain(String longDomain) {
        //获取一个新的shortDomain
        String shortDomain = createShortDomain();
        //防止并发
        LOCK.lock();
        try {
            if (DomainCache.SHORT_LONG.get(shortDomain) == null) {
                if (DomainCache.LONG_SHORT.get(longDomain) != null) {
                    return DomainCache.LONG_SHORT.get(longDomain);
                }
                DomainCache.SHORT_LONG.put(shortDomain, longDomain);
                DomainCache.LONG_SHORT.put(longDomain, shortDomain);
            } else {
                return getAndSetShortDomain(longDomain);
            }
        } finally {
            LOCK.unlock();
        }
        return shortDomain;
    }

    private static String createShortDomain() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_DOMAIN_MAX_LENGTH; i++) {
            final int index = RANDOM.nextInt(63);
            if (index != 62) {
                sb.append(RANDOM_VALUES.charAt(index));
            }
        }
        return sb.toString();
    }
}
