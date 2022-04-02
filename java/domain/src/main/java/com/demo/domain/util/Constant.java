package com.demo.domain.util;

/**
 * @Author fanzj
 * @Date 2022/4/2 19:04
 * @Version 3.0
 * @Description
 */
public interface Constant {
    /** DOMAIN_MAP **/
    String MAP_NAME = "DOMAIN_MAP";
    /** 短地址前缀 **/
    String SHORT_URL_PREFIX = "https://f.cn/";
    /** errot tips 统一提示,防止攻击 **/
    String URL_PARSE_ERROR = "url parse error";
    /**
     * 最大容量5万个
     **/
    int MAX_CAPACITY = 50000;
}
