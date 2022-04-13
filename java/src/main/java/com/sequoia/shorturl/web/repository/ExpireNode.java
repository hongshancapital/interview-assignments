package com.sequoia.shorturl.web.repository;

/**
 * @Author: yanggj
 * @Description:
 * @Date: 2022/1/5 21:37
 * @Version: 1.0.0
 */
public class ExpireNode<K> {

    //存储的域名
    private K key;
    // 过期时间(使用时间戳)
    private long expireTime;

    public ExpireNode(K key, long expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
