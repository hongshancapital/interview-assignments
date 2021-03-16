package com.sequoiacap.domain.util;

import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;

/**
 * bean属性拷贝工具
 */
public enum BeanCopierUtils {

    /**
     * 单例
     */
    X;

    private static final Map<String, BeanCopier> CACHE = Maps.newConcurrentMap();

    /**
     * 拷贝bean
     *
     * @param source - 源对象
     * @param target - 目标对象
     */
    public void copy(Object source, Object target) {
        String key = String.format("%s-%s", source.getClass().getName(), target.getClass().getName());
        CACHE.computeIfAbsent(key, x -> BeanCopier.create(source.getClass(), target.getClass(), false))
                .copy(source, target, null);
    }
}
