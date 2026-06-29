package com.jingdata.constant;

import lombok.AllArgsConstructor;

/**
 * 存储策略
 *
 * @Author
 * @Date
 */
@AllArgsConstructor
public enum  StrategyEnum {
    JVM("jvm"),
    REDIS("redis"),
    DB("db");


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
