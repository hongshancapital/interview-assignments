package com.jinblog.shorturl.service.impl;


import com.jinblog.shorturl.config.ShortConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数字url自增器
 * 直接利用数字的自增特性
 * CAS，会更快，只是可用的url数量会比较少，且无法扩展
 */
public class IntegerGenerator extends AbstractGenerator {

    private volatile boolean outOfBox = false;
    private volatile AtomicLong counter = new AtomicLong(-1);
    public IntegerGenerator(int urlMaxLen) {
        super(urlMaxLen);
    }

    @Override
    public String generate() {
        if (outOfBox) return null;
        String ans = new BigInteger(String.valueOf(counter.incrementAndGet()), 10).toString(Character.MAX_RADIX);
        if (ans.length() > getUrlMaxLen()) {
            outOfBox = true;
            return null;
        }
        return ans;
    }

    @Autowired
    private ShortConfiguration shortConfiguration;

    public void setShortConfiguration(ShortConfiguration shortConfiguration) {
        this.shortConfiguration = shortConfiguration;
    }

    @Override
    public boolean validate(String shortUrl) {
        if (shortUrl == null) {
            return false;
        }
        if (!shortUrl.startsWith(shortConfiguration.getShortUrlDomain())) {
            return false;
        }

        try {
            new BigInteger(shortUrl.replace(shortConfiguration.getShortUrlDomain(), ""), Character.MAX_RADIX);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
