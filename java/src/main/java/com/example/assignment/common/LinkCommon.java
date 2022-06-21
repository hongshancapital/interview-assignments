package com.example.assignment.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Ying
 * @date 2022.5.5
 * @description 长短域名存储公共类
 */
public class LinkCommon {
    /**
     * 直接修改 windows hosts  tt.cn 127.0.0.1
     */
    public static final String DOMAIN_PREFIX = "tt.cn";

    /**
     * 存储短链接码->原始链接 映射关系，生产环境不可如此使用
     */
    public static Map<String, String> shortLinkMap = new HashMap<>();


    /**
     * 存储长链接码->短链接 映射关系，生产环境不可如此使用
     */
    public static Map<String, String> longLinkMap = new HashMap<>();

}
