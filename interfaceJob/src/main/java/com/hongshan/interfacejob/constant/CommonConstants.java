package com.hongshan.interfacejob.constant;

public class CommonConstants {

    /**成功code*/
    public static final Integer CODE_SUCCESS = 0;
    /**成功msg*/
    public static final String SUCCESS_MSG = "success";
    /**失败code*/
    public static final Integer CODE_FAIL = 1;
    /**长域名找不到*/
    public static final Integer CODE_LONG_URL_NOT_FOUND = 1001;
    /**短域名找不到*/
    public static final Integer CODE_SHORT_URL_NOT_FOUND = 1002;
    /**短域名最大长度*/
    public static final int MAX_SHORT_URL_LENGTH = 8;
    /**长域名最大长度*/
    public static final int MAX_SUPPORT_URL_LENGTH = 1000;
    /**最大缓存数*/
    public static final int MAX_CACHE_SIZE = 10000;
    /**缓存失效时间*/
    public static final int MAX_CACHE_DAYS = 7;
    
}