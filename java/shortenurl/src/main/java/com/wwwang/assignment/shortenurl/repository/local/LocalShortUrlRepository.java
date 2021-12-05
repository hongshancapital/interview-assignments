package com.wwwang.assignment.shortenurl.repository.local;

import com.wwwang.assignment.shortenurl.entity.ByteArrObj;
import com.wwwang.assignment.shortenurl.repository.ShortUrlRepository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地内存，value是短链接信息的仓库
 */
public class LocalShortUrlRepository implements ShortUrlRepository<ByteArrObj,String> {

    private ConcurrentHashMap<ByteArrObj,String> repository = new ConcurrentHashMap<>();

    @Override
    public String get(ByteArrObj key) {
        //根据压缩后的url信息，在仓库中查找是否有对应短链接id
        return repository.get(key);
    }

    @Override
    public void put(ByteArrObj key, String value) {
        repository.put(key,value);
    }

}
