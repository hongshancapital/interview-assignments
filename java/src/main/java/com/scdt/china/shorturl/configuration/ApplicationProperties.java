package com.scdt.china.shorturl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 短地址应用配置属性
 *
 * @author ng-life
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    /**
     * 基础地址：短地址=基础地址+地址短码
     */
    private String baseUrl;

    /**
     * 基础时间：取系统初次上线时间，默认2021-11-11
     */
    private long baseTime = LocalDateTime.of(2021, 11, 11, 0, 0, 0).atZone(ZoneId.of("UTC+8")).toInstant().toEpochMilli();

    /**
     * 内存存储数量：限制大小避免OOM
     */
    private long memoryStoreCount = 999999;

    /**
     * 数据中心ID：0~31.
     */
    private int datacenterId = -1;

    /**
     * 工作节点ID：0~31.
     */
    private int workerId = -1;

    /**
     * 获取 基础地址：短地址=基础地址+地址短码
     *
     * @return baseUrl 基础地址：短地址=基础地址+地址短码
     */
    public String getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * 设置 基础地址：短地址=基础地址+地址短码
     *
     * @param baseUrl 基础地址：短地址=基础地址+地址短码
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取 基础时间：取系统初次上线时间，默认2021-11-11
     *
     * @return baseTime 基础时间：取系统初次上线时间，默认2021-11-11
     */
    public long getBaseTime() {
        return this.baseTime;
    }

    /**
     * 设置 基础时间：取系统初次上线时间，默认2021-11-11
     *
     * @param baseTime 基础时间：取系统初次上线时间，默认2021-11-11
     */
    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }

    /**
     * 获取 内存存储数量：限制大小避免OOM
     *
     * @return memoryStoreCount 内存存储数量：限制大小避免OOM
     */
    public long getMemoryStoreCount() {
        return this.memoryStoreCount;
    }

    /**
     * 设置 内存存储数量：限制大小避免OOM
     *
     * @param memoryStoreCount 内存存储数量：限制大小避免OOM
     */
    public void setMemoryStoreCount(long memoryStoreCount) {
        this.memoryStoreCount = memoryStoreCount;
    }

    /**
     * 获取 数据中心ID：0~31.
     *
     * @return datacenterId 数据中心ID：0~31.
     */
    public int getDatacenterId() {
        return this.datacenterId;
    }

    /**
     * 设置 数据中心ID：0~31.
     *
     * @param datacenterId 数据中心ID：0~31.
     */
    public void setDatacenterId(int datacenterId) {
        this.datacenterId = datacenterId;
    }

    /**
     * 获取 工作节点ID：0~31.
     *
     * @return workerId 工作节点ID：0~31.
     */
    public int getWorkerId() {
        return this.workerId;
    }

    /**
     * 设置 工作节点ID：0~31.
     *
     * @param workerId 工作节点ID：0~31.
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}
