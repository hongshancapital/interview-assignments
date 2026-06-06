package com.scdt.china.shorturl.storage;

/**
 * @Author: zhouchao
 * @Date: 2021/12/08 17:37
 * @Description:
 */
public enum DataStorageEnum {
    /**
     * 存储层策略
     */
    cache(1, "基于缓存的映射"),
    database(2, "基于数据库的映射");

    DataStorageEnum(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    /**
     * 存储层策略码
     */
    private Integer code;
    /**
     * 描述
     */
    private String des;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
