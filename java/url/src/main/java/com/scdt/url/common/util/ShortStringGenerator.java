package com.scdt.url.common.util;

import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ShortStringGenerator {


    public static String generate(String originalString, Integer limitStringLength) {
        if (originalString.length() < limitStringLength) {
            return originalString;
        }
        long numId = newId(originalString);
        //将10进制转为62进制
        return Base64Utils.encode(numId, limitStringLength);
    }


    /**
     * 获取哈希的数字id
     *
     * @param originalString 原始字符串
     * @return 获取哈希后的数字id
     */
    private static long newId(String originalString) {
        return Hashing.murmur3_32().hashUnencodedChars(originalString).padToLong();
    }
}
