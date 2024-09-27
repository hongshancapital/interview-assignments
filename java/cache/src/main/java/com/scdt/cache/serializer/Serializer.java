package com.scdt.cache.serializer;

/**
 * 序列化接口
 * @param <T>
 */
public interface Serializer<T> {

    public byte[] serialize(Object o) throws Exception;

    public T deserialize(byte[] value) throws Exception;

}