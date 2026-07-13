package com.github.shwas1.shorturl.service.generator.snowflake;

/**
 * 工作Id分配器
 */
public interface WorkIdAllocator {
    /**
     * 获取当前机器的工作Id
     * 确保不同机器的工作Id不会重复
     *
     * @param workIdBits 工作Id比特长度
     * @return 唯一的工作Id
     */
    long getWorkId(int workIdBits);
}
