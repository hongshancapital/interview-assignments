package com.ccb.domain.generate.impl;


import com.ccb.domain.generate.IDomainShorterGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author: nieyy
 * @Date: 2021/7/24 14:20
 * @Version 1.0
 * @Description: 返回短域名
 */
@Component
public class ShorterString implements IDomainShorterGenerator {

    public static char[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static Random random = new Random(System.currentTimeMillis());


    @Override
    public String generate(int length) {
        String shortUrl;
        shortUrl = generate(random.nextInt(Integer.MAX_VALUE),length);
        return shortUrl;
    }

    String generate(int seed,int length) {
        char[] sortUrl = new char[length];
        for (int i = 0; i < length; i++) {
            sortUrl[i] = VALID_CHARS[seed % VALID_CHARS.length];
            seed = random.nextInt(Integer.MAX_VALUE) % VALID_CHARS.length;
        }
        return new String(sortUrl);
    }




}
