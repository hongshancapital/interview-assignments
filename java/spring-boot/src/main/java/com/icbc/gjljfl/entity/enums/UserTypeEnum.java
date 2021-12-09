package com.icbc.gjljfl.entity.enums;

import com.icbc.gjljfl.entity.enums.base.StringKeyEnum;

/**
 * @Author: 高一平
 * @Description: 用户类型枚举类
 **/
public enum UserTypeEnum implements StringKeyEnum {
    /**
     * 普通用户
     */
    USER("0"),
    /**
     * 环卫工人
     */
    WORKER("1"),
    /**
     * 社区管理员
     */
    MANAGER("2"),
    ;

    private String key;

    UserTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}
