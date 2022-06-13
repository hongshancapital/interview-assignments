package com.scdt.domain.core.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tuxiaozhou
 * @date 2022/4/3
 */
public class ShortUrlUtil {

    /**
     * 62进制
     */
    private static final int DECIMAL = 62;
    /**
     * number自增开始
     */
    private static final int INCR_RANDOM_START = DECIMAL;
    /**
     * number自增结束
     */
    private static final int INCR_RANDOM_END = (int) Math.pow(DECIMAL, 2);
    /**
     * 开始number
     */
    private static final long START_NUMBER = (long) Math.pow(DECIMAL, 8);
    /**
     * 计数器
     */
    private static final AtomicLong COUNTER = new AtomicLong(START_NUMBER);
    private static final Character[] INIT_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    /**
     * 62进制字母数组
     */
    private static final char[] DIGITS = new char[INIT_DIGITS.length];

    static {
        initDigits();
    }

    /**
     * 初始化62进制字母数组
     */
    private static void initDigits() {
        List<Character> digitList = Lists.newArrayList(INIT_DIGITS);
        Collections.shuffle(digitList);
        for (int i = 0; i < digitList.size(); i++) {
            DIGITS[i] = digitList.get(i);
        }
    }

    private static long incrementNumber() {
        return COUNTER.addAndGet(RandomUtils.nextInt(INCR_RANDOM_START, INCR_RANDOM_END));
    }

    public static String generateShortUrl() {
        StringBuilder builder = new StringBuilder();
        long number = incrementNumber();
        while (number != 0) {
            builder.append(DIGITS[(int) (number % DECIMAL)]);
            number = number / DECIMAL;
        }
        return builder.toString();
    }

}
