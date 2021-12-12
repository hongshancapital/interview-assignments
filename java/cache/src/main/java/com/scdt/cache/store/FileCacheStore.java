package com.scdt.cache.store;

import com.scdt.cache.CacheEntry;
import com.scdt.cache.serializer.JavaSerializer;
import com.scdt.cache.serializer.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 通过文件扩展的，二级缓存
 * 利用文件存储， 通过Hash结构，将K 映射到 V存储到的对应位置{@link Pointer}
 * @param <K> key
 * @param <V> value
 */
public class FileCacheStore<K, V>  implements CacheStore<K, V> {
    public final static int DEFAULT_CAPACITY_BLOCK = 1024 * 1024 * 1024; // 1G

    public static final int MAX_VALUE_LENGTH = 4 * 1024 * 1024;

    private Serializer<CacheEntry> serializer = new JavaSerializer();

    /** 内存key 与文件位置，映射关系 */
    private final ConcurrentMap<K, Pointer> pointerMap =
            new ConcurrentHashMap<K, Pointer>();

    private final IStorageBlock storageBlock;

    private String cacheDir;

    public FileCacheStore() {
        this.cacheDir = "/temp/cache";
        if (!this.cacheDir.endsWith(File.separator)) {
            this.cacheDir += File.separator;
        }
        try {
            this.storageBlock = new StorageBlock(this.cacheDir, DEFAULT_CAPACITY_BLOCK);
        } catch (IOException e) {
            throw new IllegalArgumentException("create StorageManager error");
        }
    }

    /**
     * 存储entry
     * @param entry
     * @throws Exception
     */
    @Override
    public void store(CacheEntry<K, V> entry) throws Exception {
        if (entry == null) {
            return;
        }

        K key = entry.getKey();
        if (key == null) {
            return;
        }

        byte[] value= serializer.serialize(entry);
        if (value == null || value.length > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("value is null or too long");
        }

        Pointer pointer = pointerMap.get(key);

        if (pointer == null) {
            pointer = storageBlock.store(value);
        } else {
            pointer = storageBlock.update(pointer, value);
        }
        pointerMap.put(key, pointer);
    }

    /**
     * 通过key加载 对应的对象
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public CacheEntry load(K key) throws Exception {
        if (key == null) {
            return null;
        }
        Pointer pointer = pointerMap.get(key);
        if (pointer != null) {
            byte[] payload = storageBlock.get(pointer);
            if (payload == null) {
                return null;
            }
            return serializer.deserialize(payload);
        }
        return null;
    }

    /**
     * 通过key加载 删除的对象
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public CacheEntry remove(K key) throws Exception {
        if (key == null) {
            return null;
        }
        Pointer pointer = pointerMap.get(key);
        if (pointer != null) {
            byte[] payload = storageBlock.remove(pointer);
            pointerMap.remove(key);
            if (payload == null) {
                return null;
            }
            return serializer.deserialize(payload);
        }
        return null;
    }

    @Override
    public void close() {
        try {
            this.storageBlock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
