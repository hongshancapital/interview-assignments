package com.jingdata.core;

/**
 * 存储接口
 */
public interface Store {

    /**
     * 策略名称
     * @return
     */
    String getStrategyName();

    /**
     * 判断当前存储是否溢出
     * @return
     */
    boolean overFlow();

    /**
     * 写入
     * @param shortCode
     * @param longUrl
     * @return
     */
    String write(String shortCode, String longUrl);

    /**
     * 读取
     * @param shortCode
     * @return
     */
    String read(String shortCode);

}
