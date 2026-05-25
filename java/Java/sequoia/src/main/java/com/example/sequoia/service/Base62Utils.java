package com.example.sequoia.service;

/**
 * 短链接 - 工具类
 * @author xurui
 */
public class Base62Utils {
    private static final int RESULT_MAX_LENGTH = 8;
    private static final int SCALE = 62;
    private static final char[] BASE_62_ARRAY = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * 将long类型编码成Base62字符串
     * @param num：长链接生成的hash编码
     * @return 最终生成的短链接
     */
    public static String encodeToBase62String(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0 && sb.length() < RESULT_MAX_LENGTH) {
            sb.insert(0, BASE_62_ARRAY[(int) (num % SCALE)]);
            num /= SCALE;
        }
        return sb.toString();
    }
}