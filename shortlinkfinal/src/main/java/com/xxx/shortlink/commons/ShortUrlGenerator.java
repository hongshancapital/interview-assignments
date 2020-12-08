package com.xxx.shortlink.commons;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ShortUrlGenerator {


    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;
    private static int minLength = 8;


    //数字转62进制
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, minLength, '0');
    }

    public static String generateRandomShortUrl(String machineNo) {
        // 获取当前线程id
        long id = Thread.currentThread().getId();
        // 机器编号+线程id+时间戳生成随机不重复种子
        String randomKey = machineNo + id + System.currentTimeMillis();
        Long key = Long.valueOf(randomKey);
        try {
            // 保证时间戳不重复
            TimeUnit.MILLISECONDS.sleep(1L);
        } catch (InterruptedException e) {
            log.error("generateRandomShortUrl sleep failed");
        }
        return encode(key);
    }
}
