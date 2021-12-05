package com.wwwang.assignment.shortenurl.repository;

public interface LongUrlRepository<K,V> extends RepoProvider<K,V> {

    /**
     * 从仓库中存储的长链接信息获取真正的byte[]
     * @param longUrl
     * @return
     */
    byte[] getLongUrlBytes(V longUrl);

    /**
     * 封装压缩后的byte[]为长链接信息，因为byte[]不能直接作为Hash表的key
     * @param bytes
     * @return
     */
    V bytesToLongUrl(byte[] bytes);

}
