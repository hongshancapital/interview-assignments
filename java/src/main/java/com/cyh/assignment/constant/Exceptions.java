package com.cyh.assignment.constant;

public class Exceptions {

    // 短域名不存在时返回错误信息
    public static final String ERROR_SHORT_NOT_FOUND = "this short domain is not found";

    // 完整域名不合法时返回错误信息
    public static final String ERROR_FULL_DOMAIN_ILLEGAL = "this full domain is illegal";

    // 由于内存使用率过高停止生成新的短域名时返回错误信息
    public static final String ERROR_MEMORY_NOT_ENOUGH = "service paused";
}
