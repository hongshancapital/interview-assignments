package com.scdt.cache.serializer;

import java.io.*;


/**
 * 默认：java序列化 只适用于 Serializable{@link Serializable} 实现
 */
public class JavaSerializer implements Serializer , Serializable {
    public byte[] serialize(Object o) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            return bos.toByteArray();
        }
    }

    public Object deserialize(byte[] value) throws Exception {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(value)) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        }
    }

}