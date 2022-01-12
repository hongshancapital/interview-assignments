package com.example.demo.model.vo;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
public class UrlNode<K> {

    //存储的longUrl
    private K key;
    // 过期时间(使用时间戳)
    private long expireTime;

    public UrlNode(K key, long expireTime) {
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
