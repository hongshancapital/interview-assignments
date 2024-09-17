package com.domain.cache;

import com.domain.IDomainStoreService;
import com.domain.common.ConvertTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description:
 */
@Slf4j
//@Service
public class DomainMap{

    /**
     * 初始化队列大小
     */
    private static final int maxCapacity = 100000;

    /**
     * 短域名映射关系 key为短域名（转换后的文本），value为长域名
     */
    private static final Map<String, String> shortKeyMap = new HashMap<>(maxCapacity);

    /**
     * 长域名映射关系，key为长域名文本，value为短域名
     */
    private static final Map<String, String> longKeyMap = new HashMap<>(maxCapacity);

    public static Map<String, String> getShortKeyMap() {
        return shortKeyMap;
    }

    public static Map<String, String> getLongKeyMap() {
        return longKeyMap;
    }

    /*@Override
    public String addDomain(String text) {
        String var1 = longKeyMap.get(text);
        if (StringUtils.isEmpty(var1)) {
            var1 = ConvertTool.toString(text);
            *//**
             * 不同的长域名转换后的值可能相同
             * 处理思路：
             *      1. 类似map中红黑树结构，单独存储映射后值相同的问题；
             *      2. 调整转换函数，2次转换处理，相当于用多个字符串函数映射出最终的值，减少字符串碰撞概率。
             * 此处不做处理
             *//*
            longKeyMap.put(text, var1);
            shortKeyMap.put(var1, text);
        }
        log.info("shortKey：{}, longKey：{}", var1, text);
        return var1;
    }

    @Override
    public String getDomain(String text) {
        String var1 = shortKeyMap.get(text);
        return var1;
    }*/
}
