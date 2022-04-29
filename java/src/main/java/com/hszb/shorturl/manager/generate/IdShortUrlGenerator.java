package com.hszb.shorturl.manager.generate;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 5:16 下午
 * @Version: 1.0
 * @Description:
 */

@Slf4j
public class IdShortUrlGenerator implements ShortCodeGenerator{

    private static final int scale = 62;

    private static final int maxLen = 6;


    @Override
    public String generateCode(String longUrl) {
        long num = IdGenerator.getInstance().getNextId();
        StringBuilder sb = new StringBuilder();
        int remainder;
        do {
            remainder = (int) (num % scale);
            sb.append(ShortUrlGeneratorFactory.charArray[remainder]);
            num = num / scale;
        } while (num > scale - 1);
        sb.append(ShortUrlGeneratorFactory.charArray[(int) num]);
        char[] chars = sb.reverse().toString().toCharArray();
        String result = new String(chars);
        if (result.length() <= maxLen) {
            return result;
        }
        return null;
    }
}
