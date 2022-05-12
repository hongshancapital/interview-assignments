package com.example.urltransform.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author  fengguangwu
 * createTime  2022/5/7
 * desc
 **/
@Slf4j
public class UrlTransformUtil {
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    private static AtomicLong sequence = new AtomicLong(0);
    static final int NUMBER_61 = 61;
    static final char[] DIGITS =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g','0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


    /**
     * 自增id方式获取ShortUrl
     * @return
     */
    public static String getShortUrlWithId() {
        long myseq = sequence.incrementAndGet();
        String shortUrl = to62RadixString(myseq);
        log.info("自增id方式获取ShortUrl:{}",shortUrl);
        return shortUrl;
    }

    /**
     * 10进制转换成62进制，并查找对应digit中对应字符串
     * @param seq
     * @return
     */
    public static String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sBuilder.toString();
    }

    /**
     * 通过MD5方式获取ShortUrl
     * @param longUrl
     * @param urlLength
     * @return
     */
    public static String getShortUrlWithMd5(String longUrl, int urlLength) {
        if (urlLength < 0 || urlLength > 6) {
            throw new IllegalArgumentException("the length of url must be between 0 and 6");
        }
        //md5 longUrl
        String md5Hex = DigestUtils.md5Hex(longUrl);
        // 6 digit binary can indicate 62 letter & number from 0-9a-zA-Z
        int binaryLength = urlLength * 6;
        long binaryLengthFixer = Long.valueOf(StringUtils.repeat("1", binaryLength), 2);
        //每8位截取
        for (int i = 0; i < 4; i++) {
            String subString = StringUtils.substring(md5Hex, i * 8, (i + 1) * 8);
            //转换成二进制string
            subString = Long.toBinaryString(Long.valueOf(subString, 16) & binaryLengthFixer);
            subString = StringUtils.leftPad(subString, binaryLength, "0");
            StringBuilder sbBuilder = new StringBuilder();
            for (int j = 0; j < urlLength; j++) {
                String subString2 = StringUtils.substring(subString, j * 6, (j + 1) * 6);
                int charIndex = Integer.valueOf(subString2, 2) & NUMBER_61;
                //取对应索引位置数据
                sbBuilder.append(DIGITS[charIndex]);
            }
            String shortUrl = sbBuilder.toString();
            if (StringUtils.isBlank(shortUrl)){
                continue;
            }
            log.info("通过MD5方式获取ShortUrl:{}",shortUrl);
            return shortUrl;
        }
        return null;
    }

    public static int getMapCapacity(Map map){
        try {
            Class<?> mapType = map.getClass();
            Method capacity = mapType.getDeclaredMethod("capacity");
            capacity.setAccessible(true);
            System.out.println("capacity : " + capacity.invoke(map) + "    size : " + map.size());
            return (int)capacity.invoke(map);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static void main(String[] args) {
        System.out.println(getShortUrlWithMd5("http://www.baidu.com", 6));
        for (int i = 0; i < 3; i++) {
            System.out.println(getShortUrlWithId());
        }
    }
}
