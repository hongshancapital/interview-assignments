package com.liuxiang.service.impl;

import com.liuxiang.constant.GenerateTypeEnum;
import com.liuxiang.service.IGenerateId;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 随机生成
 *
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
public class RandomGenerateService implements IGenerateId {
    private static String BASE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static Integer LENGTH = 8;

    @Override
    public String generateType() {
        return GenerateTypeEnum.RANDOM.name();
    }

    @Override
    public String generate(String longUrl) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < LENGTH; i++) {
            int number = random.nextInt(BASE_STR.length());
            sb.append(BASE_STR.charAt(number));
        }
        return sb.toString();
    }
}
