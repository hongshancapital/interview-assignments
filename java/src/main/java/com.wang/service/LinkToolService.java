package com.wang.service;

import com.wang.util.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * desc: 链接工具service
 * author: wang
 * date: 2021-11-04
 */

@Service
public class LinkToolService {


    public final static String domain = "https://baidu.com/";//短连接域名,可以在配置文件配置
    public final static String constant = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public  String getShortUrl(Integer length) {
        int scale=62;
        Long num = IDGenerator.getRandomID();
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1&&sb.length()<length) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(constant.charAt(remainder));
            num = num / scale;
        }

        String shortUrl = StringUtils.leftPad(sb.toString(), length, '0');
        return domain + shortUrl;
    }




}
