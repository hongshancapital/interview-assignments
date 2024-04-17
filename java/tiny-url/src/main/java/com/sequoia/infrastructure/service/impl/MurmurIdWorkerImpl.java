package com.sequoia.infrastructure.service.impl;

import com.google.common.hash.Hashing;
import com.sequoia.service.IIdWorker;
import org.springframework.stereotype.Component;

/**
 * MurmurIdWorkerImpl: murmur哈希id生成器
 *
 * @author KVLT
 * @date 2022-04-01.
 */
@Component("murmurIdWorker")
public class MurmurIdWorkerImpl implements IIdWorker {

    /**
     * 基于murmurHash算法生成随机值
     * @param originUrl
     * @return
     */
    @Override
    public long nextId(String originUrl) {
        return Hashing.murmur3_128().hashUnencodedChars(originUrl).asLong();
    }

}
