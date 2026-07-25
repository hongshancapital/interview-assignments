package com.scdt.domain.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Slf4j
public class AssertUtil {
    public static void notNull(Object obj, String errorMsg) {
        if (ObjectUtil.isNull(obj)) {
            //可以改成自定义异常
            throw new RuntimeException(errorMsg);
        }
    }


    public static void isTrue(boolean param, String errorMsg) {
        if (!param) {
            throw new RuntimeException(errorMsg);
        }
    }
}
