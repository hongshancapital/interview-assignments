package com.sequoia.infrastructure.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.sequoia.service.IIdWorker;
import org.springframework.stereotype.Component;

/**
 * MurmurIdWorker
 *
 * @author KVLT
 * @date 2022-04-01.
 */
@Component("murmurIdWorker")
public class MurmurIdWorker implements IIdWorker {

    @Override
    public long nextId(String originUrl) {
        return Hashing.murmur3_32().hashString(originUrl, Charsets.UTF_8).asLong();
    }

}
