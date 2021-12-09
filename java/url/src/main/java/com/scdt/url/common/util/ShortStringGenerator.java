package com.scdt.url.common.util;

import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ShortStringGenerator {


    public static String generate(String url, Integer tinyUrlLength) {
        if (url.length() < tinyUrlLength) {
            return url;
        }
        //将String hash之后转为long
        long s = Hashing.murmur3_32().hashUnencodedChars(url).padToLong();
        //将10进制转为62进制
        return Base64Utils.encode(s, tinyUrlLength);
    }
}
