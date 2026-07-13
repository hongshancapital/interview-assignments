package com.coderdream.utils;

/**
 * 重复前端枚举
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
public enum  DuplicatedEnum {
    /**
     * DUPLICATED 重复
     */
    DUPLICATED("DUPLICATED");

    private String key;

    DuplicatedEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
