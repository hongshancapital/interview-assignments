package com.scdt.cache.store;

import java.io.Closeable;
import java.io.IOException;

/**
 * 存储的接口
 */
public interface IStorage extends Closeable {
    public static final String DATA_FILE_SUFFIX = ".data";

    /**
     * 从存储空间 获取 bytes
     *
     * @param position 位置
     * @param dest 返回数据
     */
    void get(int position, byte[] dest) throws IOException;

    /**
     * 将二进制 source{@param source} 设置到存储引擎中
     *
     * @param position  位置
     * @param source 源数据
     */
    void put(int position, byte[] source) throws IOException;
}
