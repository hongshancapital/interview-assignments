package com.weige.shorturl.common;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName Storage
 * @Description 全局内存存储
 * @Author zwwang14
 * @Date 2022/1/24 16:20
 * @Version 1.0
 */
public class Storage {
    private static HashMap<String,String> map = new HashMap<>();

    public static void set(String key,String value){
        if (!StringUtils.isEmpty(key) && !Objects.isNull(value)){
            map.put(key,value);
        }
    }

    public static String find(String key){
        if (!StringUtils.isEmpty(key)){
            return map.get(key);
        }
        return null;
    }
}
