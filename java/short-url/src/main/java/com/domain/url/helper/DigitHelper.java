package com.domain.url.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * 数字操作工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DigitHelper {
    private static final long COUNT_START = 10000L;
    private static final long COUNT_MAX = 218340105584895L; // 62^8 - 1
    private static final LongAdder COUNT = new LongAdder();
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    static {
        // 设置自增Id的初始值
        COUNT.add(COUNT_START);
    }

    /**
     * 生成62进制字符串
     *
     * @return 62进制字符串
     */
    public static String generateHex62() {
        return hex10To62(next());
    }

    /**
     * 10进制转62进制
     *
     * @param number 10进制数字
     * @return 62进制字符串
     */
    private static String hex10To62(long number) {
        StringBuilder sBuilder = new StringBuilder();
        do {
            int remainder = (int) (number % 62);
            sBuilder.append(DIGITS[remainder]);
            number = number / 62;
        } while (number != 0);

        return sBuilder.reverse().toString();
    }

    /**
     * 获取自增Id
     * <p>
     * 使用 LongAdder 实现，高并发下单机性能比AtomicLong更优秀，线程安全
     *
     * @return 自增Id
     */
    private static long next() {
        final long count = COUNT.longValue();

        COUNT.increment();

        if (COUNT.longValue() > COUNT_MAX) {
            COUNT.add(-COUNT.longValue() + 10000);
        }

        return count;
    }
}
