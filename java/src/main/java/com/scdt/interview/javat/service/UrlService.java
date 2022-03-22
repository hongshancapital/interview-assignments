package com.scdt.interview.javat.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlService {

    private static final AtomicLong count = new AtomicLong(0);
    private static char[] alpMap = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static Map<Long, String> idToL = new ConcurrentHashMap<>();
    private static Map<String, String> lToS = new ConcurrentHashMap<>();
    private static Set<String> lUrlSet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private static int lowerCaseOffset = -61;
    private static int upperCaseOffset = -55;
    private static int digitOffset = -48;
    private static int maxLength = 8;

    // 长地址转短地址
    public String toSUrl(String lUrl) {
        UrlValidator urlValidator = new UrlValidator();
        boolean valid = urlValidator.isValid(lUrl);
        if (!valid) {
            return null;
        }
        String sUrl = lToS.get(lUrl);
        if (Strings.isNotBlank(sUrl)) {
            return sUrl;
        }

        // 并发控制，如果有相同长域名正在处理，睡眠1s
        if (!lUrlSet.add(lUrl)) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ex) {
                // do what you want: like logging
            } finally {
                return lToS.get(lUrl);
            }
        }

        try {
            Long id = count.getAndIncrement();
            List<Integer> index = toReverseSixTwoMode(id);
            if (index.size() > maxLength) {
                return null;
            }

            StringBuffer sb = new StringBuffer();
            for (Integer i : index) {
                sb.append(alpMap[i]);
            }
            idToL.put(id, lUrl);
            lToS.put(lUrl, sb.toString());

            return sb.toString();
        } finally {
            lUrlSet.remove(lUrl);
        }

    }

    // 短地址转长地址
    public String toLUrl(String sUrl) {
        if (sUrl == null || sUrl.length() > maxLength) {
            return null;
        }

        Long id = 0L;
        Long base = 1L;
        // 倒序计算
        for (int i = 0; i < sUrl.length(); i++) {
            Integer a = charToI(sUrl.charAt(i));
            if (a == null) {
                return null;
            }
            id += a * base;
            base *= 62;
        }

        return idToL.get(id);
    }

    // 10进制转62进制，倒序
    private List<Integer> toReverseSixTwoMode(long n) {
        List<Integer> result = new LinkedList<>();
        do {
            int r = (int)(n % 62);
            n = n / 62;
            result.add(r);
        } while (n != 0);

        return result;
    }

    // char to i
    private Integer charToI(char c) {
        int ascii = c;
        if (Character.isLowerCase(c)) {
            return ascii + lowerCaseOffset;
        } else if (Character.isUpperCase(c)) {
            return ascii + upperCaseOffset;
        } else if (Character.isDigit(c)) {
            return ascii + digitOffset;
        } else {
            return null;
        }
    }
}
