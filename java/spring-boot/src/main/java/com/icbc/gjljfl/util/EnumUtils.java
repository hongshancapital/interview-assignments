package com.icbc.gjljfl.util;

import com.icbc.gjljfl.entity.enums.base.IntKeyEnum;
import com.icbc.gjljfl.entity.enums.base.StringKeyEnum;

/**
 * @Author: gaowh
 * @Date: 2021/8/2
 * @Description:
 **/
public class EnumUtils {

    /**
     * 获取枚举值对象
     *
     * @param key       查询键
     * @param enumClass 值对象枚举类型
     * @param <T>       值对象枚举类型
     * @return 值对象
     */
    public static <T extends IntKeyEnum
            > T getByKey(Integer key, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (key.equals(each.getKey())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 获取枚举值对象
     *
     * @param key       查询键
     * @param enumClass 值对象枚举类型
     * @param <T>       值对象枚举类型
     * @return 值对象
     */
    public static <T extends StringKeyEnum> T getByKey(String key, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (key.equals(each.getKey())) {
                return each;
            }
        }
        return null;
    }
}
