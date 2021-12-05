package com.wwwang.assignment.shortenurl.repository.local;

import com.wwwang.assignment.shortenurl.entity.ByteArrObj;
import com.wwwang.assignment.shortenurl.repository.LongUrlRepository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地内存，value是长链接信息的仓库
 */
public class LocalLongUrlRepository implements LongUrlRepository<String , ByteArrObj> {

    private ConcurrentHashMap<String, ByteArrObj> repository = new ConcurrentHashMap<>();

    @Override
    public ByteArrObj get(String key) {
        //根据短链接的唯一id，在仓库中查找是否有对应url
        return repository.get(key);
    }

    @Override
    public void put(String key, ByteArrObj value) {
        repository.put(key,value);
    }

    @Override
    public byte[] getLongUrlBytes(ByteArrObj longUrl) {
        return longUrl.data;
    }

    @Override
    public ByteArrObj bytesToLongUrl(byte[] bytes) {
        return new ByteArrObj(bytes);
    }
}
