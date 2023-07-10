package com.coderdream.utils;

import com.google.common.hash.Hashing;

/**
 * 通用工具类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
public class CommonUtil {
    /**
     * 使用murmurhash算法返回10进制数
     *
     * @param param 待Hash的字符串
     * @return 生成的Hash编码
     */
    public static long murmurHash32(String param) {
        if (param == null || "".equals(param.trim())) {
            return 0;
        }

        return Hashing.murmur3_32().hashUnencodedChars(param).padToLong();
    }

    /**
     * 生成以“0”组成的字符串，位数由入参决定
     *
     * @param zeroBit 补“0”的位数
     * @return 生成的字符串
     */
    public static String generateZeroString(Integer zeroBit) {
        if (zeroBit <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < zeroBit; i++) {
            stringBuffer.append("0");
        }

        return stringBuffer.toString();
    }

}