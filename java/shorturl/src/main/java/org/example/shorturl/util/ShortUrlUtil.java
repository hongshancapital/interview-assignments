package org.example.shorturl.util;

import cn.hutool.cache.Cache;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.properties.UrlTransformProperty;
import org.example.shorturl.service.UrlTransformService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 进制转化工具
 * 1、将十进制的数字转换为指定进制的字符串；
 * 2、将其它进制的数字（字符串形式）转换为十进制的数字
 *
 * @author bai
 * @date 2022/3/19 20:44
 */
@Slf4j
@Component
public class ShortUrlUtil {
    /** 前缀 http */
    private static final String HTTP = "http://";
    /** 前缀 https */
    private static final String HTTPS = "https://";
    /** 短链接url格式模板 */
    private static String urlFormat;
    /** prefix */
    private static String prefix;
    /** ssl */
    private static Boolean ssl;
    @Resource
    private UrlTransformProperty transformProperty;
    
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        ssl = transformProperty.getSsl();
        prefix = transformProperty.getPrefix();
    }
    
    /**
     * 根据配置获取短链接url模板
     *
     * @return {@link Cache}<{@link String}, {@link String}>
     */
    public static String getShortUrlFormat() {
        if (ObjectUtil.isEmpty(urlFormat)) {
            synchronized (UrlTransformService.class) {
                if (urlFormat == null) {
                    urlFormat = ssl ? HTTPS + prefix + "/" : HTTP + prefix + "/";
                }
            }
        }
        return urlFormat;
    }
    
    /**
     * 获取短链接
     *
     * @param code 代码
     * @return {@link String}
     */
    public static String getShortUrl(String code) {
        return getShortUrlFormat() + code;
    }
    
    
    /**
     * 十进制62进制
     *
     * @param num 全国矿工工会
     * @return {@link String}
     */
    public static String decimalToSixtyTwo(long num) {
        if (num <= 0) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        //余数
        long remainder;
        
        while (num > 0) {
            remainder = num % 62;
            
            //0-9
            if (remainder < 10) {
                sb.append((char) ('0' + remainder));
            }
            //A-Z
            else if (remainder < 36) {
                sb.append((char) ('A' + remainder - 10));
            }
            //a-z
            else {
                sb.append((char) ('a' + remainder - 36));
            }
            
            num = num / 62;
        }
        
        //因为在上面的循环过程中，后一次循环本应是计算出来的高位字符，但是却被我们放在字符串的最后面，因此最终结果需要再反转一次
        return sb.reverse().toString();
    }
    
    /**
     * 62进制转十进制
     *
     * @param numStr 62进制字符串
     * @return java.lang.String
     * @author zifangsky
     * @date 2020/11/13 12:27
     * @since 1.0.0
     */
    public static long sixtyTwoToDecimal(String numStr) {
        //最后转换完成之后的十进制数字
        long num = 0;
        //字符串中的具体某一个字符
        int idx;
        
        for (int i = 0; i < numStr.length(); i++) {
            idx = numStr.charAt(numStr.length() - 1 - i);
            
            if (idx >= 'a') {
                //idx = 'a' + remainder - 36，于是可以推导出：remainder = idx + 36 - 'a'
                //num = remainder * 62^i
                num += (idx + 36 - 'a') * Math.pow(62, i);
            }
            else if (idx >= 'A') {
                //idx = 'A' + remainder - 10，于是可以推导出：remainder = idx + 10 - 'A'
                num += (idx + 10 - 'A') * Math.pow(62, i);
            }
            else {
                //idx = '0' + remainder，于是可以推导出：remainder = idx - '0'
                num += (idx - '0') * Math.pow(62, i);
            }
        }
        return num;
    }
}
