package com.icbc.gjljfl.entity.enums;

import com.icbc.gjljfl.entity.enums.base.StringKeyEnum;

/**
 * @Author: 高一平
 * @Description: 用户类型枚举类
 **/
public enum CommonEnum implements StringKeyEnum {
    /**
     * 无效
     */
    DISABLE("0"),
    /**
     * 有效
     */

    ABLE("1"),
    ;

    private String key;

    CommonEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}
