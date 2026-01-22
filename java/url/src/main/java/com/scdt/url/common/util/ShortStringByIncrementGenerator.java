package com.scdt.url.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

@UtilityClass
@Slf4j
public class ShortStringByIncrementGenerator {

    private static final LongAdder COUNTER = new LongAdder();

    public static String generate(String originalString, Integer limitStringLength) {
        if (originalString.length() < limitStringLength) {
            return originalString;
        }
        long numId = newId(limitStringLength);
        //将10进制转为62进制
        return Base64Utils.encode(numId, limitStringLength);
    }

    /**
     * 获取自增的数字id
     *
     * @param limitStringLength 字符串长度
     * @return 获取自增的新id
     */
    private static long newId(Integer limitStringLength) {
        final long idNum = COUNTER.longValue();
        COUNTER.increment();
        if (COUNTER.longValue() > getMax(limitStringLength)) {
            throw new IllegalStateException("newId is out of range");
        }
        return idNum;
    }

    /**
     * 获取限制自增限制长度
     *
     * @param limitStringLength 字符串限制长度
     * @return 自增最大值
     */

    private static double getMax(Integer limitStringLength) {
        return Math.pow(Base64Utils.getScale(), limitStringLength) - 1;
    }
}
