package com.addr.node.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommonUtils {

    private static final int BINARY = 0x2;

    private static final int NUMBER_61 = 0x0000003d;

    private Map<String, String> longShortUrlMap = new ConcurrentHashMap<String, String>();

    private static Map<String, String> shortLongUrlMap = new ConcurrentHashMap<String, String>();

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static String shorten(String longUrl) {
        int urlLength=8;
        /*if (urlLength < 0 || urlLength > 8) {
            throw new IllegalArgumentException("the length of url must be between 0 and 6");
        }*/
        String md5Hex = DigestUtils.md5Hex(longUrl);
        // 6 digit binary can indicate 62 letter & number from 0-9a-zA-Z
        int binaryLength = urlLength * 6;
        long binaryLengthFixer = Long.valueOf(StringUtils.repeat("1", binaryLength), BINARY);
        for (int i = 0; i < 4; i++) {
            String subString = StringUtils.substring(md5Hex, i * 8, (i + 1) * 8);
            subString = Long.toBinaryString(Long.valueOf(subString, 16) & binaryLengthFixer);
            subString = StringUtils.leftPad(subString, binaryLength, "0");
            StringBuilder sbBuilder = new StringBuilder();
            for (int j = 0; j < urlLength; j++) {
                String subString2 = StringUtils.substring(subString, j * 6, (j + 1) * 6);
                int charIndex = Integer.valueOf(subString2, BINARY) & NUMBER_61;
                sbBuilder.append(Constants.DIGITS[charIndex]);
            }
            String shortUrl = sbBuilder.toString();
            if (lookupLong(shortUrl) != null) {
                continue;
            } else {
                return Constants.DOMAIN_PREFIX+shortUrl;
            }
        }
        return null;
    }

    public static String lookupLong(String shortUrl) {
        String longUrl = null;
        Lock readLock = readWriteLock.readLock();
        try {
            readLock.lock();
            longUrl = shortLongUrlMap.get(shortUrl);
        } catch (Exception e) {
        } finally {
            readLock.unlock();
        }
        return longUrl;
    }

    public static void main(String[] args){
        CommonUtils util = new CommonUtils();
        String url = util.shorten("https://download.csdn.net/download/weixin_38669628/11496718");
        System.out.println("EEE:"+url);

    }

}
