package com.scdt.cache.store;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 存储块，提前分配一块磁盘空间， 供后面使用访问
 *
 * TODO 未来需要优化的， 由于被多次删除，则导致磁盘内部空间会出现空间碎片， 因此需要对磁盘进行碎片整理
 */
public class StorageBlock implements IStorageBlock {
    private final int capacity;

    private IStorage storage;

    /**
     * 当前位置
     */
    private final AtomicInteger currentOffset = new AtomicInteger(0);

    public StorageBlock(String dir, int capacity) throws IOException{
        this.capacity = capacity;
        storage = new FileChannelStorage(dir, capacity);
    }


    /**
     * 根据 pointer{@link Pointer} 获取对应的位置
     * @param pointer
     * @return
     * @throws IOException
     */
    @Override
    public byte[] get(Pointer pointer) throws IOException {
        byte [] payload = new byte[pointer.getLength()];
        storage.get(pointer.getPosition(), payload);
        return payload;
    }

    /**
     * 从存储中 删除 对应的空间
     * @param pointer
     * @return
     * @throws IOException
     */
    @Override
    public byte[] remove(Pointer pointer) throws IOException {
        byte [] payload = get(pointer);
        return payload;
    }

    @Override
    public Pointer store(byte[] payload) throws IOException {
        Pointer pointer = allocate(payload);
        if (pointer == null) {
            return null;
        }
        return store(pointer, payload);
    }

    /**
     * 更新数据
     * @param pointer
     * @param payload
     * @return
     * @throws IOException
     */
    @Override
    public Pointer update(Pointer pointer, byte[] payload) throws IOException {
        /**
         * 判断要更新数据，在原来空间是否足够；
         * 如果不足够，重新分配一块内存
         */
        if (pointer.getLength() >= payload.length) {
            return store(new Pointer(pointer.getPosition(), payload.length), payload); // should always return a new pointer
        } else {
            /**
             * 如果足够直接返回
             */
            return store(payload);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.storage != null) {
            this.storage.close();
        }
    }

    /**
     * 从磁盘中分配出一块空间
     * @param payload
     * @return
     */
    private Pointer allocate(byte[] payload) {
        int payloadLength = payload.length;
        int allocationOffset = currentOffset.addAndGet(payloadLength);
        if(this.capacity < allocationOffset){
            return null;
        }
        Pointer allocation = new Pointer(allocationOffset - payloadLength, payloadLength);
        return allocation;
    }


    private Pointer store(Pointer pointer, byte[] payload) throws IOException {
        storage.put(pointer.getPosition(), payload);
        return pointer;
    }


}
