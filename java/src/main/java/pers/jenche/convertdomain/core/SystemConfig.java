package pers.jenche.convertdomain.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Copyright (c) 2020 By www.jenche.cn
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @date 2020/5/12 14:46
 * @description 系统配置
 */
public class SystemConfig {
    /**
     * 初始化一个常量字段，用来保存执行转换的URL的数据
     */
    public static final Map<String, String> SHORT_URI_STORE = new LinkedHashMap<>();

    //语言包的系统常量
    public static final Map<String, ResourceBundle> LANG_MESSAGE = new HashMap<>();
}
